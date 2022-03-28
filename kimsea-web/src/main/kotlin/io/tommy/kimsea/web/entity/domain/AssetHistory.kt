package io.tommy.kimsea.web.entity.domain

import io.tommy.kimsea.web.enums.AssetHistoryStatusEnum
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class AssetHistory (
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,

    val assetId:Long,

    @OneToOne
    @JoinColumn(name = "from_user_id")
    val from:User,

    @OneToOne
    @JoinColumn(name = "to_user_id")
    val to:User? = null,
    val toAddress:String? = null,
    var price:Long = 0,
    var fee:Long = 0,

    @Enumerated(EnumType.STRING)
    var status: AssetHistoryStatusEnum,

    val description:String? = null,
    var regDttm:LocalDateTime = LocalDateTime.now(),
)