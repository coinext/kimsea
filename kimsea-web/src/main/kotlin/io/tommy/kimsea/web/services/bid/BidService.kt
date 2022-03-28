package io.tommy.kimsea.web.services.bid

import io.tommy.kimsea.web.annotations.SoftTransactional
import io.tommy.kimsea.web.configs.Const
import io.tommy.kimsea.web.configs.Const.Companion.BID_DURATION_MINUTES
import io.tommy.kimsea.web.entity.domain.AssetHistory
import io.tommy.kimsea.web.entity.repository.*
import io.tommy.kimsea.web.enums.AssetHistoryStatusEnum
import io.tommy.kimsea.web.enums.AssetStatusEnum
import io.tommy.kimsea.web.enums.CodeEnum
import io.tommy.kimsea.web.enums.OrderStatusEnum
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.services.PushService
import io.tommy.kimsea.web.utils.logger
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import kotlin.streams.toList

@Service
class BidService(
    private val orderRepository: OrderRepository,
    private val walletRepository: WalletRepository,
    private val assetHistoryRepository: AssetHistoryRepository,
    private val assetRepository: AssetRepository,
    private val userRepository: UserRepository,
    private val pushService: PushService
) {
    val log = logger()

    fun sellFeePrice(price:Long) : Long {
        return (Const.TRADE_SELL_FEE_PERCENT * price / 100).toLong()
    }

    fun buyFeePrice(price:Long) : Long {
        return (Const.TRADE_BUY_FEE_PERCENT * price / 100).toLong()
    }

    fun durationBidPeriod(dttm:LocalDateTime) : LocalDateTime {
        return dttm.plusMinutes(BID_DURATION_MINUTES)
    }

    @SoftTransactional
    fun doBid(assetId: Long) {
        log.info("------------ doBid ${assetId} ----------")
        val now = LocalDateTime.now()
        val asset = assetRepository.findById(assetId).orElseThrow { WebException(CodeEnum.FAIL) }
        asset.also {
            if (it.status == AssetStatusEnum.DELETE) {
                throw WebException(CodeEnum.FAIL)
            }

            if (it.bidEndDttm == null || it.bidEndDttm!!.isAfter(now)) {
                return
            }
        }


        val order = orderRepository.findOneByAssetIdAndStatus(assetId, OrderStatusEnum.SELL)

        // SELL없고 BUY만 있는경우.
        if (order == null) {
            asset.apply {
                bidEndDttm = null
            }

            val candidateOrders = orderRepository.findByAssetIdAndStatusOrderByPriceDesc(assetId, OrderStatusEnum.BUY)
            if (candidateOrders.isEmpty()) {
                return
            }

            candidateOrders.forEach { candidateOrder ->
                candidateOrder.apply {
                    status = OrderStatusEnum.CANCEL_BUY
                    completedDttm = now
                }

                val candidateWallet = walletRepository.findById(candidateOrder.userId).orElseThrow{WebException(CodeEnum.FAIL)}
                candidateWallet.apply {
                    usingGem -= (candidateOrder.price + candidateOrder.fee)
                    availableGem += (candidateOrder.price + candidateOrder.fee)
                }

                pushService.send(
                    title = "구매취소",
                    msg = "'${asset.name}' 구매가 취소되었습니다.",
                    userId = candidateOrder.userId)
            }

            log.info("[ASSET_ID:${asset}] SELL = N, BUY = Y")
            return
        }

        //SELL은 있고, BUY 없는경우.
        val _candidateOrders = orderRepository.findByAssetIdAndStatusOrderByPriceDesc(assetId, OrderStatusEnum.BUY)
        if (_candidateOrders.isEmpty()) {
            log.info("[ASSET_ID:${asset}] SELL = Y, BUY = N")

            order.apply {
                status = OrderStatusEnum.CANCEL_SELL
                completedDttm = now
            }

            asset.apply {
                status = AssetStatusEnum.PLACED
                bidEndDttm = null
            }

            pushService.send(
                title = "구매취소",
                msg = "'${asset.name}' 구매가 취소되었습니다.",
                userId = order.userId)
            return
        }


        //SELL, BUY 모두 있는경우.
        val candidateOrders = _candidateOrders
        val candidateOrdersWithoutCompletingBid = _candidateOrders.stream().skip(1).toList()
        val candidateOrder = _candidateOrders[0]

        val wallet = walletRepository.findById(order.userId).orElseThrow{WebException(CodeEnum.FAIL)}
        val candidateWallet = walletRepository.findById(candidateOrder.userId).orElseThrow{WebException(CodeEnum.FAIL)}

        //success
        if (order.price <= candidateOrder.price) {
            log.info("[ASSET_ID:${asset}] SELL = Y, BUY = Y ::: BID COMPLETED")
            val candidateUser = userRepository.findById(candidateOrder.userId).orElseThrow { WebException(CodeEnum.FAIL) }

            wallet.apply {
                availableGem += (candidateOrder.price - order.fee)
            }

            order.apply {
                status = OrderStatusEnum.COMPLETED_SELL
                to = candidateOrder.from
                completedDttm = now
            }

            asset.apply {
                status = AssetStatusEnum.PLACED
                price = candidateOrder.price
                bidEndDttm = null
                owner = candidateUser
            }

            pushService.send(
                title = "판매완료",
                msg = "'${asset.name}' 판매가 완료되었습니다.",
                userId = order.userId)

            candidateWallet.apply {
                usingGem -= (candidateOrder.price + candidateOrder.fee)
            }

            candidateOrder.apply {
                status = OrderStatusEnum.COMPLETED_BUY
                to = order.from
                completedDttm = now
            }

            pushService.send(
                title = "구매완료",
                msg = "'${asset.name}' 구매가 완료되었습니다.",
                userId = candidateOrder.userId)

            candidateOrdersWithoutCompletingBid.forEach { candidateOrder ->
                candidateOrder.apply {
                    status = OrderStatusEnum.CANCEL_BUY
                    completedDttm = now
                }

                val candidateWallet = walletRepository.findById(candidateOrder.userId).orElseThrow{WebException(CodeEnum.FAIL)}
                candidateWallet.apply {
                    usingGem -= (candidateOrder.price + candidateOrder.fee)
                    availableGem += (candidateOrder.price + candidateOrder.fee)
                }

                pushService.send(
                    title = "구매취소",
                    msg = "'${asset.name}' 구매가 취소되었습니다.",
                    userId = candidateOrder.userId)
            }

            AssetHistory(
                assetId = asset.id!!,
                status = AssetHistoryStatusEnum.SELL,
                from = order.from,
                to = candidateOrder.from,
                price = candidateOrder.price,
                fee = candidateOrder.fee,
            ).run {
                assetHistoryRepository.save(this)
            }
        } else {
        //fail
            log.info("[ASSET_ID:${asset}] SELL = Y, BUY = Y ::: BID FAILED")

            order.apply {
                status = OrderStatusEnum.CANCEL_SELL
                completedDttm = now
            }

            asset.apply {
                status = AssetStatusEnum.PLACED
                bidEndDttm = null
            }

            pushService.send(
                title = "판매취소",
                msg = "'${asset.name}' 판매가 취소되었습니다.",
                userId = order.userId)

            candidateOrders.forEach { candidateOrder ->
                candidateOrder.apply {
                    status = OrderStatusEnum.CANCEL_BUY
                    completedDttm = now
                }

                val candidateWallet = walletRepository.findById(candidateOrder.userId).orElseThrow{WebException(CodeEnum.FAIL)}
                candidateWallet.apply {
                    usingGem -= (candidateOrder.price + candidateOrder.fee)
                    availableGem += (candidateOrder.price + candidateOrder.fee)
                }

                pushService.send(
                    title = "구매취소",
                    msg = "'${asset.name}' 구매가 취소되었습니다.",
                    userId = candidateOrder.userId)
            }
        }
    }
}