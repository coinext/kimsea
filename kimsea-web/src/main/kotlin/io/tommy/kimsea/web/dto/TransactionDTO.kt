package io.tommy.kimsea.web.dto

import io.tommy.kimsea.web.entity.domain.Transaction

class TransactionDTO {

    data class MyTransactionDTO (
        val depositTransactions:List<Transaction>,
        val withdrawTransactions:List<Transaction>
    )
}