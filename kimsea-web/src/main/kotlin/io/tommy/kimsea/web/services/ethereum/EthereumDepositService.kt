package io.tommy.kimsea.web.services.ethereum

import com.fasterxml.jackson.databind.ObjectMapper
import io.tommy.kimsea.web.annotations.SoftTransactional
import io.tommy.kimsea.web.configs.Const
import io.tommy.kimsea.web.dto.fetch.FetchTransactionDTO
import io.tommy.kimsea.web.enums.CoinEnum
import io.tommy.kimsea.web.enums.TransactionCategoryEnum
import io.tommy.kimsea.web.enums.TransactionStatusEnum
import io.tommy.kimsea.web.providers.EthereumProvider
import io.tommy.kimsea.web.services.PushService
import io.tommy.kimsea.web.services.TransactionService
import io.tommy.kimsea.web.services.WalletService
import io.tommy.kimsea.web.utils.DateUtil
import io.tommy.kimsea.web.utils.depositLogger
import io.tommy.kimsea.web.utils.rangeTo
import io.tommy.kimsea.web.utils.wrapper.Web3jWrapper
import org.springframework.stereotype.Service
import org.web3j.protocol.core.DefaultBlockParameter
import org.web3j.protocol.core.methods.response.EthBlock
import org.web3j.protocol.core.methods.response.Transaction
import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.atomic.AtomicLong


@Service
class EthereumDepositService(
    private val web3JWrapper: Web3jWrapper,
    private val transactionService: TransactionService,
    private val walletService: WalletService,
    private val ethereumProvider: EthereumProvider,
    private val pushService: PushService
) {
    val log = depositLogger()
    var depositQueue: Queue<FetchTransactionDTO> = LinkedList()

    val objectMapper = ObjectMapper()

    companion object {
        var OLD_PENDING_DEPOSIT_PROCESS_RUNNING = false
        var PAST_DEPOSIT_PROCESS_RUNNING = false
        var FUTURE_DEPOSIT_PROCESS_RUNNING = false
    }

    @SoftTransactional
    fun doDeposit(currentBlockNumber:BigInteger, userId:Long, _createdDttm:LocalDateTime, tx: Transaction) : Boolean {
        val existTransaction = transactionService.getPendingDepositTranasctionById(tx.hash)
        if (existTransaction != null && existTransaction.status != TransactionStatusEnum.PENDING) return false

        log.info("deposit ::: ${tx.blockNumber} - ${tx.hash} - ${tx.value} - ${tx.from} - ${tx.to} - ${tx.transactionIndex} - ${tx.input}")

        //트랜잭션이 이미 있을때,
        val amount = Convert.fromWei(tx.value.toBigDecimal(), Convert.Unit.ETHER)
        val gemAmount = amount.multiply(BigDecimal(Const.ETH_TO_GEM_AMOUNT)).toInt()

        //트랜잭션이 없을때,
        if (existTransaction == null) {
            log.info("deposit :: existTransaction == null")
            transactionService.registerTransaction(
                io.tommy.bluesea.web.entity.domain.Transaction(
                    id = tx.hash,
                    blockNum = tx.blockNumber,
                    userId = userId,
                    orgTx = tx.hash,
                    coin = CoinEnum.ETH,
                    category = TransactionCategoryEnum.DEPOSIT,
                    status = TransactionStatusEnum.PENDING,
                    fromAddress = tx.from,
                    toAddress = tx.to,
                    amount = amount,
                    gemAmount = gemAmount,
                    fee = 0.toBigDecimal(),
                    confirm = 0,
                    createdDttm = _createdDttm
                )
            )
            return false
        } else {
            //트랜잭션이 있을때
            log.info("deposit :: existTransaction != null")

            return existTransaction.apply {
                confirm = currentBlockNumber.subtract(blockNum).toInt()
                regDttm = LocalDateTime.now()
                if (transactionService.isConfirmDepositTransaction(confirm)) {
                    status = TransactionStatusEnum.COMPLETED
                    completedDttm = LocalDateTime.now()
                }
                fromAddress = tx.from
                toAddress = tx.to
                this.amount = amount
                this.gemAmount = gemAmount

            }.run {
                if (status == TransactionStatusEnum.COMPLETED) {
                    walletService.transferToGem(userId, gemAmount)

                    pushService.send(
                        title = "입금완료",
                        msg = "ETH ($amount)개 입금이 완료되었습니다 ",
                        userId = userId)

                    try {
                        ethereumProvider.withdrawAll(walletService.getWalletByUserId(userId), walletService.getAdminAddress())
                    } catch (ex :Exception) {
                        log.error("[ADMIN_ADDRESS_COLLECT FAILED] ${ex.message}")
                    }
                    true
                }
                false
            }
        }
    }

    private fun fetchTransacion(currentBlockNumber:BigInteger, timestamp:BigInteger, tx:Transaction) {
        if (transactionService.isDepositTransaction(tx)
            && walletService.isDepositedWalletByAddress(tx.to)
        ) {
            try {
                walletService.getWalletByAddress(tx.to)?.let {
                    depositQueue.offer(FetchTransactionDTO(currentBlockNumber, it.userId!!, DateUtil.timestampToDatetime(timestamp), tx))
                }
            } catch (ex: Exception) {
                log.error(ex.message)
            }
        }
    }

    private fun fetchBlock(currentBlockNumber:BigInteger, ethBlock:EthBlock) {
        ethBlock.block?.let { block ->
            block.transactions.forEach {
                val tx = it as Transaction
                fetchTransacion(currentBlockNumber, block.timestamp, tx)
            }
        }
    }

    private fun doFutureDeposit(currentBlockNumber:BigInteger) {
        FUTURE_DEPOSIT_PROCESS_RUNNING = true
        log.info(
            "* [Future] Begin `scanning` depositTransactions :: [start block] :: ($currentBlockNumber)"
        )

        web3JWrapper.dynamicWeb3j().replayPastAndFutureBlocksFlowable(DefaultBlockParameter.valueOf(currentBlockNumber), true).subscribe(
            { ethBlock ->
                ethBlock.block?.let { block ->
                    log.info("> [Future] Scanning block number : ${ethBlock.block?.number}, tx size : ${ethBlock.block?.transactions?.size}")
                    fetchBlock(block.number, ethBlock)
                }
            },
            { ex ->
                log.error("[Future] scanning error : ${ex.message}")
                FUTURE_DEPOSIT_PROCESS_RUNNING = false
            },
            {
                log.info("[Future] scanning done.")
                FUTURE_DEPOSIT_PROCESS_RUNNING = false
            })
    }

    //처음 구동시에 한번만 동작.
    private fun doPastDeposit(currentBlockNumber:BigInteger) {
        PAST_DEPOSIT_PROCESS_RUNNING = true
        val beginPastBlockNumber = transactionService.getBeginPastBlockNumber(currentBlockNumber)
        val endPastBlockNumber = currentBlockNumber
        if (beginPastBlockNumber.compareTo(BigInteger.ZERO) == 0) {
            log.info("[Past] scanning done.")
            return
        }

        log.info(
            "* [Past] Begin `scanning` depositTransactions :: [start block / current block] :: (${beginPastBlockNumber} / $endPastBlockNumber) - ${
                endPastBlockNumber.subtract(
                    beginPastBlockNumber
                )
            } block ago"
        )

        val parallerJobCnt = AtomicLong(0)
        val blockNumers = beginPastBlockNumber.rangeTo(endPastBlockNumber).toList()
        if (blockNumers.size <=0 ) {
            log.info("[Past] scanning done.")
            return
        }

        blockNumers
            .chunked(100)
            .map {
                web3JWrapper.dynamicWeb3j().replayPastBlocksFlowable(
                    DefaultBlockParameter.valueOf(it.first()),
                    DefaultBlockParameter.valueOf(it.last()),
                    true
                )
            }.toList().forEach {
                parallerJobCnt.addAndGet(1)
                it.subscribe({ ethBlock ->
                    ethBlock.block?.let { block ->
                        log.info("> [Past ${parallerJobCnt.get()}] Scanning block number : ${ethBlock.block.number}, tx size : ${ethBlock.block?.transactions?.size}")
                        fetchBlock(currentBlockNumber, ethBlock)
                    }
                },
                    { ex ->
                        parallerJobCnt.addAndGet(-1)
                        if (parallerJobCnt.get() <= 0) {
                            log.error("[Past] scanning error : ${ex.message}")
                            PAST_DEPOSIT_PROCESS_RUNNING = false
                        }

                    },
                    {
                        parallerJobCnt.addAndGet(-1)
                        if (parallerJobCnt.get() <= 0) {
                            log.info("[Past] scanning done.")
                        }
                    })
            }
    }

    private fun doOldPendingDeposit(currentBlockNumber:BigInteger) {
        OLD_PENDING_DEPOSIT_PROCESS_RUNNING = true
        val pendingTransactions = transactionService.getPendingTransactionsFromCurrentBlockNumber(currentBlockNumber)
        log.info(
            "* [Pending] Begin `scanning` depositTransactions :: size :: (${pendingTransactions.size})"
        )
        if (pendingTransactions.size <= 0) {
            log.info("[Pending] scanning done.")
            OLD_PENDING_DEPOSIT_PROCESS_RUNNING = false
            return
        }

        val parallerJobCnt = AtomicLong(0)
        pendingTransactions.chunked(10).parallelStream().forEach {
            it.forEach {
                parallerJobCnt.addAndGet(1)
                web3JWrapper.dynamicWeb3j().ethGetTransactionByHash(it.id).flowable().subscribe(
                    {tx ->
                        fetchTransacion(currentBlockNumber, BigInteger.ZERO, tx.transaction.get())
                    },
                    {ex ->
                        parallerJobCnt.addAndGet(-1)
                        if (parallerJobCnt.get() <= 0) {
                            log.error("[Pending] scanning error : ${ex.message}")
                            OLD_PENDING_DEPOSIT_PROCESS_RUNNING = false
                        }
                    },
                    {
                        parallerJobCnt.addAndGet(-1)
                        if (parallerJobCnt.get() <= 0) {
                            log.info("[Pending] scanning done.")
                            OLD_PENDING_DEPOSIT_PROCESS_RUNNING = false
                        }
                    }
                )
            }
        }
    }

    fun publishDepositTransactions() {
        val currentBlockNumber = web3JWrapper.web3j().ethBlockNumber().sendAsync().get().blockNumber
        if (!OLD_PENDING_DEPOSIT_PROCESS_RUNNING) doOldPendingDeposit(currentBlockNumber)
        if (!PAST_DEPOSIT_PROCESS_RUNNING) doPastDeposit(currentBlockNumber)
        if (!FUTURE_DEPOSIT_PROCESS_RUNNING) doFutureDeposit(currentBlockNumber)
    }

    fun consumeDepositTransactions(consumer: (BigInteger, Long, LocalDateTime, Transaction) -> Unit) {
        var isLoop = true
        while(isLoop) {
            val fetchRow = depositQueue.poll()
            if (fetchRow != null) {
                consumer(fetchRow.currentBlockNumber, fetchRow.userId, fetchRow.createdDttm, fetchRow.tx)
            } else {
                isLoop = false
            }
        }
    }
}
