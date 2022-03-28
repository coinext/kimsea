package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.Order
import io.tommy.kimsea.web.enums.OrderStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface OrderRepository : CrudRepository<Order, Long> {


    fun findByUserIdAndAssetIdAndStatusOrderByPriceDesc(userId:Long, assetId:Long, status:OrderStatusEnum) : Order?

    @Query("select sum(c.price) from Order c where c.status = :status")
    fun sumByStatus(status: OrderStatusEnum) : Optional<Long>

    fun findOneByIdAndStatus(id:Long, status:OrderStatusEnum) : Order?

    fun findOneByAssetIdAndStatus(id:Long, status:OrderStatusEnum) : Order?

    fun findByIdAndStatus(id:Long, status:OrderStatusEnum) : Order

    fun findByStatusOrderByRegDttmAsc(status:OrderStatusEnum) : List<Order>

    fun findByStatusInOrderByRegDttmAsc(status:List<OrderStatusEnum>) : List<Order>

    fun findByUserIdAndStatus(userId:Long, status:OrderStatusEnum) : List<Order>

    fun findByUserIdAndAssetIdAndStatus(userId:Long, assetId:Long, status:OrderStatusEnum) : Order?

    fun findFirstByAssetIdAndStatus(assetId:Long, status:OrderStatusEnum) : Order?

    fun countAllByAssetIdAndStatus(assetId:Long, status:OrderStatusEnum) : Long

    fun findByAssetIdAndStatusOrderByPriceDesc(assetId:Long, status:OrderStatusEnum) : List<Order>

    fun findByAssetIdOrderByRegDttmDesc(assetId:Long, pageable: Pageable) : List<Order>

    fun findFirstByAssetIdAndStatusOrderByPriceDesc(assetId:Long, status:OrderStatusEnum) : Order

    fun existsByAssetIdAndStatusOrderByPriceDesc(assetId:Long, status:OrderStatusEnum) : Boolean
}