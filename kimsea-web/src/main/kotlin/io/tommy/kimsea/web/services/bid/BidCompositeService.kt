package io.tommy.kimsea.web.services.bid

import io.tommy.kimsea.web.entity.repository.OrderRepository
import io.tommy.kimsea.web.enums.OrderStatusEnum
import io.tommy.kimsea.web.utils.logger
import org.springframework.stereotype.Service

@Service
class BidCompositeService(
    private val bidService: BidService,
    private val orderRepository: OrderRepository,
) {

    val log = logger()

    fun doBid() {
        orderRepository.findByStatusInOrderByRegDttmAsc(listOf(OrderStatusEnum.SELL, OrderStatusEnum.BUY))
            .distinctBy { order -> order.assetId }
            .forEach {
            log.info("Ordered AssetId : ${it.assetId}")
            try {
                bidService.doBid(it.assetId)
            } catch (ex:Exception) {
                log.error(ex.message)
            }
        }
    }
}