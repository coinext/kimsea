package io.tommy.kimsea.web.entity.domain

import io.tommy.kimsea.web.enums.OrderStatusEnum
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "`order`")
data class Order (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,

    val assetId:Long,

    val userId:Long,

    @OneToOne
    @JoinColumn(name = "from_user_id")
    val from:User,

    @OneToOne
    @JoinColumn(name = "to_user_id")
    var to:User? = null,

    var price:Long = 0,
    var fee:Long = 0,

    @Enumerated(EnumType.STRING)
    var status: OrderStatusEnum,

    val description:String? = null,
    var regDttm:LocalDateTime = LocalDateTime.now(),
    var completedDttm:LocalDateTime? = null,
)