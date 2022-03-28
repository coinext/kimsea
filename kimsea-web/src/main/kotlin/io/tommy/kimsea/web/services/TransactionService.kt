package io.tommy.kimsea.web.services

import io.tommy.kimsea.web.entity.repository.AssetRepository
import io.tommy.kimsea.web.entity.repository.TransactionRepository
import io.tommy.kimsea.web.enums.CoinEnum
import io.tommy.kimsea.web.enums.TransactionCategoryEnum
import io.tommy.kimsea.web.enums.TransactionStatusEnum
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.web3j.protocol.core.methods.response.Transaction
import java.math.BigInteger
import java.time.LocalDateTime

@Service
class TransactionService(
    val transactionRepository: TransactionRepository,
    val assetRepository: AssetRepository
) {
    val confirm = BigInteger("3")

    fun getMyWithdrawTransactions(userId:Long, pageNo:Int, pageSize:Int) : List<io.tommy.bluesea.web.entity.domain.Transaction> {
        return transactionRepository.findByCoinAndCategoryOrderByRegDttmDesc(CoinEnum.KIMSEA, TransactionCategoryEnum.WITHDRAW, PageRequest.of(pageNo, pageSize)).map {
            it.asset = assetRepository.findByIdOrNull(it.assetId)
            it
        }.toList()
    }

    fun getMyDepositTransactions(userId:Long, pageNo:Int, pageSize:Int) : List<io.tommy.bluesea.web.entity.domain.Transaction> {
        return transactionRepository.findByCoinAndCategoryOrderByRegDttmDesc(CoinEnum.ETH, TransactionCategoryEnum.DEPOSIT, PageRequest.of(pageNo, pageSize))
    }

    fun isDepositTransaction(tx:Transaction) = (tx.input == "0x" && tx.from != tx.to && tx.value > BigInteger.ZERO)
    fun isConfirmDepositTransaction(confirm:Int) = (confirm > 0 && confirm > this.confirm.toInt())

    fun getNextScanBlockNumber(currentBlockNumer:BigInteger) = currentBlockNumer.subtract(confirm).subtract(BigInteger.ONE)

    fun getPendingTransactionsFromCurrentBlockNumber(blockNumber:BigInteger) = transactionRepository.findByCoinAndCategoryAndStatusAndBlockNumLessThan(CoinEnum.ETH, TransactionCategoryEnum.DEPOSIT, TransactionStatusEnum.PENDING, blockNumber)

    fun getBeginPastBlockNumber(currentBlockNumber: BigInteger) = transactionRepository.findFirstByCoinAndCategoryAndStatusAndBlockNumLessThanOrderByBlockNumAsc(CoinEnum.ETH, TransactionCategoryEnum.DEPOSIT, TransactionStatusEnum.PENDING, currentBlockNumber)?.blockNum ?: transactionRepository.findFirstByCoinAndCategoryAndStatusAndBlockNumLessThanOrderByBlockNumDesc(CoinEnum.ETH, TransactionCategoryEnum.DEPOSIT, TransactionStatusEnum.COMPLETED, currentBlockNumber)?.blockNum ?: BigInteger.ZERO

    fun getPendingDepositTranasctionById(id:String) = transactionRepository.findByIdOrNull(id)

    fun registerTransaction(transaction:io.tommy.bluesea.web.entity.domain.Transaction) {
        transactionRepository.save(transaction)
    }

    fun getPendingTransactions(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum, regDttm:LocalDateTime) = transactionRepository.findByCoinAndCategoryAndStatusAndRegDttmBefore(coin, category, status, regDttm)
    fun getPendingTransactions(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum, retryCnt:Int) = transactionRepository.findByCoinAndCategoryAndStatusAndRetryCntLessThan(coin, category, status, retryCnt)
    fun getPendingTransactions(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum, pageRequest: PageRequest) = transactionRepository.findByCoinAndCategoryAndStatus(coin, category, status, pageRequest)
    fun getPendingTransactions(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum) = transactionRepository.findByCoinAndCategoryAndStatus(coin, category, status)
}