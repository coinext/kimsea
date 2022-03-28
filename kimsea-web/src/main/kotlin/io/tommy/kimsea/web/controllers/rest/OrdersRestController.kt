package io.tommy.kimsea.web.controllers.rest

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.Order
import io.tommy.kimsea.web.enums.OrderStatusEnum
import io.tommy.kimsea.web.services.OrderService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/orders")
class OrdersRestController(
    private val orderService: OrderService
) {

    @GetMapping("/getOrdersByAssetIdAndStatus")
    fun getOrdersByAssetIdAndStatus(@RequestParam assetId:Long, @RequestParam orderStatus:OrderStatusEnum): Response<List<Order>> {
        return Response(orderService.getOrdersByAssetIdAndStatus(assetId, orderStatus))
    }

    @GetMapping("/getOrdersByAssetId")
    fun getOrdersByAssetId(@RequestParam assetId:Long, @RequestParam(required = false, defaultValue = "0") pageNo:Int, @RequestParam(required = false, defaultValue = "20") pageSize:Int): Response<List<Order>> {
        return Response(orderService.getOrdersByAssetId(assetId, pageNo, pageSize))
    }

}