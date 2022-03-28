package io.tommy.kimsea.web.services

import io.tommy.kimsea.web.entity.domain.Order
import io.tommy.kimsea.web.entity.repository.OrderRepository
import io.tommy.kimsea.web.enums.OrderStatusEnum
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class OrderService(
    private val orderRepository: OrderRepository
) {

    fun getOrdersByAssetIdAndStatus(assetId:Long, orderStatusEnum: OrderStatusEnum = OrderStatusEnum.BUY) : List<Order> {
        return orderRepository.findByAssetIdAndStatusOrderByPriceDesc(assetId, orderStatusEnum)
    }

    fun getOrdersByAssetId(assetId:Long, pageNo:Int, pageSize:Int) : List<Order> {
        return orderRepository.findByAssetIdOrderByRegDttmDesc(assetId, PageRequest.of(pageNo, pageSize))
    }
}