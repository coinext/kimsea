package io.tommy.kimsea.web.services.asset

import com.fasterxml.jackson.databind.ObjectMapper
import io.tommy.kimsea.web.annotations.SoftTransactional
import io.tommy.kimsea.web.configs.Const.Companion.MIN_SELL_PRICE
import io.tommy.kimsea.web.dto.AssetDTO
import io.tommy.kimsea.web.entity.domain.Order
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.entity.repository.AssetRepository
import io.tommy.kimsea.web.entity.repository.OrderRepository
import io.tommy.kimsea.web.entity.repository.WalletRepository
import io.tommy.kimsea.web.enums.AssetStatusEnum
import io.tommy.kimsea.web.enums.CodeEnum
import io.tommy.kimsea.web.enums.OrderStatusEnum
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.services.bid.BidService
import io.tommy.kimsea.web.utils.logger
import org.springframework.data.jpa.repository.Lock
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import javax.persistence.LockModeType

@Service
class AssetTradeService(
    private val assetRepository:AssetRepository,
    private val walletRepository:WalletRepository,
    private val orderRepository: OrderRepository,
    private val bidService: BidService
) {
    val log = logger()
    val objectMapper = ObjectMapper()


    @SoftTransactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun cancelBuyAsset(user:User, reqCancelBuyAssetDTO: AssetDTO.ReqCancelBuyAssetDTO) : Order {
        val asset = assetRepository.findById(reqCancelBuyAssetDTO.assetId).orElseThrow {WebException(CodeEnum.NOT_EXIST_ASSET)}.also {
            if (it.status != AssetStatusEnum.PLACED && it.status != AssetStatusEnum.SELL) {
                throw WebException(CodeEnum.INVALID_ASSET_STATUS)
            }
        }

        val order = orderRepository.findByUserIdAndAssetIdAndStatus(user.id!!, asset.id!!, OrderStatusEnum.BUY)
            ?: throw WebException(CodeEnum.NOT_EXIST_ORDER)

        val wallet = walletRepository.findById(user.id!!).orElseThrow { WebException(CodeEnum.NOT_EXIST_WALLET) }

        orderRepository.existsByAssetIdAndStatusOrderByPriceDesc(asset.id!!, OrderStatusEnum.SELL).also {
            if (!it) {
                asset.bidEndDttm = null
            }
        }

        order.apply {
            status = OrderStatusEnum.CANCEL_BUY
            regDttm = LocalDateTime.now()
        }

        wallet.apply {
            usingGem = usingGem - (order.price + order.fee)
            availableGem = availableGem + (order.price + order.fee)
        }
        return order
    }

    @SoftTransactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun modifyBuyAsset(user:User, reqModifyBuyAssetDTO: AssetDTO.ReqModifyBuyAssetDTO) : Order {
        val feePrice = bidService.buyFeePrice(reqModifyBuyAssetDTO.price)
        val totalBuyPrice = reqModifyBuyAssetDTO.price + feePrice

        val asset = assetRepository.findById(reqModifyBuyAssetDTO.assetId).orElseThrow {WebException(CodeEnum.NOT_EXIST_ASSET)}.also {
            if (it.status != AssetStatusEnum.PLACED && it.status != AssetStatusEnum.SELL) {
                throw WebException(CodeEnum.INVALID_ASSET_STATUS)
            }
        }

        val order = orderRepository.findByUserIdAndAssetIdAndStatus(user.id!!, asset.id!!, OrderStatusEnum.BUY)
            ?: throw WebException(CodeEnum.NOT_EXIST_ORDER)

        val wallet = walletRepository.findById(user.id!!).orElseThrow { WebException(CodeEnum.NOT_EXIST_WALLET) }

        if (reqModifyBuyAssetDTO.price < MIN_SELL_PRICE) {
            throw WebException(CodeEnum.SELL_UNDER_MIN_SELL_PRICE)
        }

        if (wallet.availableGem < totalBuyPrice) {
            throw WebException(CodeEnum.NOT_AVAILABLE_PRICE_IN_WALLET)
        }

        if (asset.bidEndDttm == null) {
            asset.bidEndDttm = bidService.durationBidPeriod(LocalDateTime.now())
        }

        wallet.apply {
            availableGem = availableGem - totalBuyPrice + (order.price + order.fee)
            usingGem = usingGem + totalBuyPrice - (order.price + order.fee)
        }

        order.apply {
            status = OrderStatusEnum.BUY
            price = reqModifyBuyAssetDTO.price
            regDttm = LocalDateTime.now()
        }
        return order
    }

    @SoftTransactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun buyAsset(user:User, reqBuyAssetDTO: AssetDTO.ReqBuyAssetDTO) : Order {
        //구매 최소 금액 체크.
        if (reqBuyAssetDTO.price < MIN_SELL_PRICE) {
            throw WebException(CodeEnum.SELL_UNDER_MIN_SELL_PRICE)
        }

        val feePrice = bidService.buyFeePrice(reqBuyAssetDTO.price)
        val totalBuyPrice = reqBuyAssetDTO.price + feePrice

        //지갑 잔액 체크 / 처리
        walletRepository.findById(user.id!!).orElseThrow { WebException(CodeEnum.NOT_EXIST_WALLET) }.also {
            if (it.availableGem < totalBuyPrice) {
                throw WebException(CodeEnum.NOT_AVAILABLE_PRICE_IN_WALLET)
            }
        }.apply {
            availableGem -= totalBuyPrice
            usingGem += totalBuyPrice
        }

        //NFT의 상태체크 / 처리
        val asset = assetRepository.findById(reqBuyAssetDTO.assetId).orElseThrow {WebException(CodeEnum.NOT_EXIST_ASSET)}.also {
            if (it.status != AssetStatusEnum.PLACED && it.status != AssetStatusEnum.SELL) {
                throw WebException(CodeEnum.INVALID_ASSET_STATUS)
            }
        }.apply {
            if (bidEndDttm == null) {
                bidEndDttm = bidService.durationBidPeriod(LocalDateTime.now())
            }
        }

        //거래 생성
        return Order(
            userId = user.id!!,
            assetId = asset.id!!,
            from = user,
            to = asset.owner,
            price = reqBuyAssetDTO.price,
            fee = feePrice,
            status = OrderStatusEnum.BUY,
        ).run {
            orderRepository.save(this)
        }
    }

    @SoftTransactional
    fun cancelSellAsset(user:User, reqCancelSellAssetDTO: AssetDTO.ReqCancelSellAssetDTO) : Order {
        walletRepository.existsById(user.id!!).also {
            if (!it) {
                throw WebException(CodeEnum.NOT_EXIST_WALLET)
            }
        }

        val asset = assetRepository.findById(reqCancelSellAssetDTO.assetId).orElseThrow {WebException(CodeEnum.NOT_EXIST_ASSET)}
        asset.also {
            if (it.status != AssetStatusEnum.SELL) {
                throw WebException(CodeEnum.INVALID_ASSET_STATUS)
            }

        }

        asset.apply {
            if (!orderRepository.existsByAssetIdAndStatusOrderByPriceDesc(id!!, OrderStatusEnum.BUY)) {
                status = AssetStatusEnum.PLACED
                bidEndDttm = null
                regDttm = LocalDateTime.now()
            }
        }

        val order = orderRepository.findByUserIdAndAssetIdAndStatus(user.id!!, asset.id!!, OrderStatusEnum.SELL)
            ?: throw WebException(CodeEnum.NOT_EXIST_ORDER)
        order.apply {
            status = OrderStatusEnum.CANCEL_SELL
        }
        return order
    }

    @SoftTransactional
    fun modifySellAsset(user:User, reqModifySellAssetDTO: AssetDTO.ReqModifySellAssetDTO) : Order {
        val feePrice = bidService.sellFeePrice(reqModifySellAssetDTO.price)

        if (!walletRepository.existsById(user.id!!)) {
            throw WebException(CodeEnum.NOT_EXIST_WALLET)
        }

        if (reqModifySellAssetDTO.price < MIN_SELL_PRICE) {
            throw WebException(CodeEnum.SELL_UNDER_MIN_SELL_PRICE)
        }

        val asset = assetRepository.findById(reqModifySellAssetDTO.assetId).orElseThrow {WebException(CodeEnum.NOT_EXIST_ASSET)}
        asset.also {
            if (it.status != AssetStatusEnum.SELL) {
                throw WebException(CodeEnum.INVALID_ASSET_STATUS)
            }

            if (it.price == reqModifySellAssetDTO.price) {
                throw WebException(CodeEnum.ALREADY_SELL_SAME_PRICE)
            }
        }

        asset.apply {
            bidEndDttm = bidService.durationBidPeriod(LocalDateTime.now())
            regDttm = LocalDateTime.now()
        }


        val order = orderRepository.findByUserIdAndAssetIdAndStatus(user.id!!, asset.id!!, OrderStatusEnum.SELL)
            ?: throw WebException(CodeEnum.NOT_EXIST_ORDER)
        return order.apply {
            price = reqModifySellAssetDTO.price
            regDttm = LocalDateTime.now()
            fee = feePrice
        }
    }

    @SoftTransactional
    fun sellAsset(user:User, reqSellAssetDTO: AssetDTO.ReqSellAssetDTO) : Order {
        reqSellAssetDTO.also {
            if (it.price < MIN_SELL_PRICE) {
                throw WebException(CodeEnum.SELL_UNDER_MIN_SELL_PRICE)
            }
        }

        val asset = assetRepository.findById(reqSellAssetDTO.assetId).orElseThrow {WebException(CodeEnum.NOT_EXIST_ASSET)}.also {
            if (it.status != AssetStatusEnum.PLACED) {
                throw WebException(CodeEnum.INVALID_ASSET_STATUS)
            }
        }

        asset.apply {
            status = AssetStatusEnum.SELL
            bidEndDttm = bidService.durationBidPeriod(LocalDateTime.now())
        }

        walletRepository.existsById(user.id!!).also {
            if (!it) {
                throw WebException(CodeEnum.NOT_EXIST_WALLET)
            }
        }

        return Order(
            userId = user.id!!,
            assetId = asset.id!!,
            from = user,
            price = reqSellAssetDTO.price,
            fee = bidService.sellFeePrice(reqSellAssetDTO.price),
            status = OrderStatusEnum.SELL,
        ).run {
            orderRepository.save(this)
        }
    }
}