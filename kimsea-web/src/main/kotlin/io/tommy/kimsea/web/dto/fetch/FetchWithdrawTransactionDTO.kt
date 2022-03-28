package io.tommy.kimsea.web.dto.fetch

import io.tommy.kimsea.web.entity.domain.Asset
import org.web3j.protocol.core.methods.response.TransactionReceipt

data class FetchWithdrawTransactionDTO (
    val isSucceed:Boolean,
    val transactionReceipt: TransactionReceipt,
    val assets:List<Asset>,
    val ex:Exception?
)