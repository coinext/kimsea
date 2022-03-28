package io.tommy.kimsea.web.entity.domain

import io.tommy.kimsea.web.enums.CoinEnum
import io.tommy.kimsea.web.enums.TransactionCategoryEnum
import io.tommy.kimsea.web.enums.TransactionStatusEnum
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "`transaction`")
data class Transaction (
    @Id
    var id:String,

    var blockNum:BigInteger,
    val userId:Long,

    @Enumerated(EnumType.STRING)
    val coin: CoinEnum,

    var orgTx:String? = null,

    @Enumerated(EnumType.STRING)
    val category: TransactionCategoryEnum,

    @Enumerated(EnumType.STRING)
    var status: TransactionStatusEnum,

    var assetId : Long = 0,
    var fromAddress:String,
    var toAddress:String,
    var amount:BigDecimal,
    var gemAmount:Int = 0,
    val fee:BigDecimal,
    var confirm:Int,
    var revertReason:String? = null,
    var retryCnt:Int = 0,
    var createdDttm:LocalDateTime,
    var regDttm:LocalDateTime = LocalDateTime.now(),
    var completedDttm:LocalDateTime? = null,

    @Transient
    var asset:Asset? = null
)