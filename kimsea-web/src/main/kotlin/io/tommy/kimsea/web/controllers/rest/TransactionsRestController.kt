package io.tommy.kimsea.web.controllers.rest

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.entity.domain.Transaction
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.services.TransactionService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/transactions")
class TransactionsRestController(
    val transactionService: TransactionService
) {

    @GetMapping("/getMyWithdrawTransactions")
    fun getMyWithdrawTransactions(user: User, @RequestParam pageNo:Int, @RequestParam pageSize:Int): Response<List<Transaction>> {
        return Response(transactionService.getMyWithdrawTransactions(user.id!!, pageNo, pageSize))
    }

    @GetMapping("/getMyDepositTransactions")
    fun getMyDepositTransactions(user: User, @RequestParam pageNo:Int, @RequestParam pageSize:Int): Response<List<Transaction>> {
        return Response(transactionService.getMyDepositTransactions(user.id!!, pageNo, pageSize))
    }
}