package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.Transaction
import io.tommy.kimsea.web.enums.CoinEnum
import io.tommy.kimsea.web.enums.TransactionCategoryEnum
import io.tommy.kimsea.web.enums.TransactionStatusEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.math.BigInteger
import java.time.LocalDateTime

@Repository
interface TransactionRepository : CrudRepository<Transaction, String> {

    fun findFirstByCoinAndCategoryAndStatusAndBlockNumLessThanOrderByBlockNumAsc(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum, blockNum:BigInteger) : Transaction?
    fun findByCoinAndCategoryAndStatusAndBlockNumLessThan(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum, blockNum: BigInteger) : List<Transaction>
    fun findFirstByCoinAndCategoryAndStatusAndBlockNumLessThanOrderByBlockNumDesc(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum, blockNum:BigInteger) : Transaction?

    fun findFirstByStatusAndBlockNumLessThanOrderByBlockNumAsc(status: TransactionStatusEnum, blockNum:BigInteger) : Transaction?
    fun findByStatusAndBlockNumLessThan(status: TransactionStatusEnum, blockNum:BigInteger) : List<Transaction>
    fun findByStatus(status: TransactionStatusEnum) : List<Transaction>
    fun findFirstByStatusOrderByBlockNumAsc(status: TransactionStatusEnum) : Transaction?
    fun findFirstByStatusOrderByBlockNumDesc(status: TransactionStatusEnum) : Transaction?

    fun findByCoinAndCategoryAndStatusAndRegDttmBefore(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum, regDttm:LocalDateTime) : List<Transaction>
    fun findByCoinAndCategoryAndStatusAndRetryCntLessThan(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum, retryCnt:Int) : List<Transaction>

    fun findByCoinAndCategoryOrderByRegDttmDesc(coin: CoinEnum, category: TransactionCategoryEnum, pageable:Pageable) : List<Transaction>
    fun findByCoinAndCategory(coin: CoinEnum, category: TransactionCategoryEnum) : List<Transaction>
    fun findFirstByCoinAndCategoryAndAssetIdAndStatusIn(coin: CoinEnum, category: TransactionCategoryEnum, assetId:Long, statuses: List<TransactionStatusEnum>) : Transaction?
    fun findByCoinAndCategoryAndStatus(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum) : List<Transaction>
    fun findByCoinAndCategoryAndStatus(coin: CoinEnum, category: TransactionCategoryEnum, status: TransactionStatusEnum, pageable:Pageable) : List<Transaction>
    fun findFirstByStatusAndBlockNumLessThanOrderByBlockNumDesc(status: TransactionStatusEnum, blockNum:BigInteger) : Transaction?
}