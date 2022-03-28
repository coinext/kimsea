package io.tommy.kimsea.web.services.ethereum

import com.fasterxml.jackson.databind.ObjectMapper
import io.tommy.kimsea.web.annotations.SoftTransactional
import io.tommy.kimsea.web.configs.Const.Companion.WITHDRAW_BATCH_SIZE
import io.tommy.kimsea.web.dto.fetch.FetchWithdrawTransactionDTO
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.repository.AssetRepository
import io.tommy.kimsea.web.enums.CodeEnum
import io.tommy.kimsea.web.enums.CoinEnum
import io.tommy.kimsea.web.enums.TransactionCategoryEnum
import io.tommy.kimsea.web.enums.TransactionStatusEnum
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.providers.EthereumProvider
import io.tommy.kimsea.web.services.TransactionService
import io.tommy.kimsea.web.services.WalletService
import io.tommy.kimsea.web.utils.depositLogger
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.exceptions.ContractCallException
import java.time.LocalDateTime
import java.util.*


@Service
class EthereumWithdrawService(
    private val transactionService: TransactionService,
    private val walletService: WalletService,
    private val ethereumProvider: EthereumProvider,
    private val assetRepository: AssetRepository
) {
    val log = depositLogger()
    var withdrawQueue: Queue<FetchWithdrawTransactionDTO> = LinkedList()

    val objectMapper = ObjectMapper()

    fun minAndSendNftToken(toAddresses:List<String>, assets: List<Asset>, consumer: (Boolean, List<Asset>, TransactionReceipt, Exception?) -> Unit) {
        ethereumProvider.mintAndSendNftToken(toAddresses, assets, consumer)
    }

    @SoftTransactional
    fun publishWithdrawTransactions() {
        //오래된 남아있는 PROCESS 상태의 트랜잭션을 정리함.(현재기준 30분이상동안 남아있는 트랜잭션대상)
        val oldWithdrawTransactions = transactionService.getPendingTransactions(CoinEnum.KIMSEA, TransactionCategoryEnum.WITHDRAW, TransactionStatusEnum.PROCESS, LocalDateTime.now().minusMinutes(30))
        if (!oldWithdrawTransactions.isEmpty()) {
            //성공인지 실패인지 선조회한다 (TokenURI, transaction으로 점검)
            oldWithdrawTransactions.forEach { oldWithdrawTransaction ->
                try {
                    if (!ethereumProvider.isMintedAsset(oldWithdrawTransaction.assetId)) {
                        throw WebException(CodeEnum.WITHDRAW_FAIL)
                    }

                    val transactionReceipt = ethereumProvider.getTransactionByTxId(oldWithdrawTransaction.orgTx!!)
                    if (!transactionReceipt.isStatusOK) {
                        throw WebException(CodeEnum.WITHDRAW_FAIL)
                    }

                    withdrawQueue.offer(FetchWithdrawTransactionDTO(true, transactionReceipt, listOf(assetRepository.findByIdOrNull(oldWithdrawTransaction.assetId)!!), null))
                } catch (ex:ContractCallException) {
                    withdrawQueue.offer(FetchWithdrawTransactionDTO(false, TransactionReceipt(), listOf(assetRepository.findByIdOrNull(oldWithdrawTransaction.assetId)!!), WebException(CodeEnum.WITHDRAW_FAIL)))
                } catch (ex:WebException) {
                    if (ex.codeEnum == CodeEnum.NOT_EXIST_TRANSACTION_RECEIPT
                        || ex.codeEnum == CodeEnum.DO_NOT_TRADE_WITHDRAW_ASSET
                        || ex.codeEnum == CodeEnum.WITHDRAW_CANCEL) {
                        withdrawQueue.offer(FetchWithdrawTransactionDTO(false, TransactionReceipt(), listOf(assetRepository.findByIdOrNull(oldWithdrawTransaction.assetId)!!), WebException(CodeEnum.WITHDRAW_FAIL)))
                    } else {
                        log.error("withdraw[2] oldWithdrawTransactions process failed, skiped : ${ex.message}")
                    }
                } catch (ex:Exception) {
                    log.error("withdraw oldWithdrawTransactions process failed, skiped : ${ex.message}")
                }
            }
            return
        }

        //신규로 등록된 PROCESS 상태의 트랜잭션이 있는경우, 나중에 처리하도록 이번배치는 스킵함.
        val withdrawProcessingTransations = transactionService.getPendingTransactions(CoinEnum.KIMSEA, TransactionCategoryEnum.WITHDRAW, TransactionStatusEnum.PROCESS)
        if (!withdrawProcessingTransations.isEmpty()) {
            return
        }

        //현재 PENDING 상태의 트랜잭션을 처리함.
        val withdrawTransations = transactionService.getPendingTransactions(CoinEnum.KIMSEA, TransactionCategoryEnum.WITHDRAW, TransactionStatusEnum.PENDING, PageRequest.of(0, WITHDRAW_BATCH_SIZE))
        if (withdrawTransations.isEmpty()) {
            return
        }

        withdrawTransations.forEach {
            it.status = TransactionStatusEnum.PROCESS
        }
        val withdrawingAssets = withdrawTransations.map {t -> assetRepository.findById(t.assetId).get()}.filter { a -> true }.toList()

        val toAddresses = withdrawTransations.map{ tx -> tx.toAddress}.filter { true }.toList()

        val preTxId = ethereumProvider.getMintAndSendNftTokenPreTxId(
            toAddresses,
            withdrawingAssets
        )

        withdrawTransations.forEach {
            it.orgTx = preTxId
        }

        log.info("* getMintAndSendNftTokenPreTxId : ${preTxId}")

        minAndSendNftToken(
            toAddresses,
            withdrawingAssets
        ) { isSucceed, assets, transactionReceipt, ex ->
            log.info("minAndSendNftToken done : ${transactionReceipt.transactionHash}")
            withdrawQueue.offer(FetchWithdrawTransactionDTO(isSucceed, transactionReceipt, assets, ex))
        }
    }

    fun consumeWithdrawTransactions(consumer: (Boolean, TransactionReceipt, List<Asset>, Exception?) -> Unit) {
        var isLoop = true
        while(isLoop) {
            val fetchRow = withdrawQueue.poll()
            if (fetchRow != null) {
                consumer(fetchRow.isSucceed, fetchRow.transactionReceipt, fetchRow.assets, fetchRow.ex)
            } else {
                isLoop = false
            }
        }
    }
}
