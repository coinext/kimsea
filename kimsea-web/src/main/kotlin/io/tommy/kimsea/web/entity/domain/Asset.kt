package io.tommy.kimsea.web.entity.domain

import io.tommy.kimsea.web.enums.AssetMimeEnum
import io.tommy.kimsea.web.enums.AssetStatusEnum
import io.tommy.kimsea.web.enums.CategoryEnum
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Asset(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,

    @OneToOne
    @JoinColumn(name = "creator_id")
    val creator:User,

    @OneToOne
    @JoinColumn(name = "owner_id")
    var owner:User,

    @Enumerated(EnumType.STRING)
    val category: CategoryEnum,

    @Enumerated(EnumType.STRING)
    val mime: AssetMimeEnum = AssetMimeEnum.IMAGE,

    @Enumerated(EnumType.STRING)
    var status: AssetStatusEnum,

    val nftAddress:String = "0x0",
    var name:String,
    var price:Long = 0,
    val description:String,

    val imgUrl:String,
    val prevImgUrl:String,
    val thumbImgUrl:String,
    val attachFileUrl:String,
    val prevAttachFileUrl:String,
    var metadataUrl:String? = null,

    val attrs:String = "[]",
    var regDttm:LocalDateTime,
    var bidEndDttm:LocalDateTime?,
    val delDttm:LocalDateTime?,
    var mintDttm:LocalDateTime?,

    @Transient
    var sellOrder:Order? = null,

    @Transient
    var buyOrder:Order? = null,

    @Transient
    var withdrawTx:Transaction? = null
)