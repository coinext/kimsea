package io.tommy.kimsea.web.dto.fetch

import org.web3j.protocol.core.methods.response.Transaction
import java.math.BigInteger
import java.time.LocalDateTime

data class FetchTransactionDTO(
    val currentBlockNumber:BigInteger,
    val userId:Long,
    val createdDttm:LocalDateTime,
    val tx:Transaction
)