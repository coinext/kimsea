package io.tommy.kimsea.web.services.asset

import com.fasterxml.jackson.databind.ObjectMapper
import io.tommy.kimsea.web.annotations.SoftTransactional
import io.tommy.kimsea.web.configs.Const.Companion.CONTRACT_CALL_RETRY_MAX_CNT
import io.tommy.kimsea.web.configs.Const.Companion.WITHDRAW_FEE
import io.tommy.kimsea.web.dto.AssetDTO
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.domain.AssetHistory
import io.tommy.kimsea.web.entity.domain.Transaction
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.entity.repository.AssetHistoryRepository
import io.tommy.kimsea.web.entity.repository.AssetRepository
import io.tommy.kimsea.web.entity.repository.TransactionRepository
import io.tommy.kimsea.web.entity.repository.WalletRepository
import io.tommy.kimsea.web.enums.*
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.services.PushService
import io.tommy.kimsea.web.utils.withdrawLogger
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.web3j.crypto.Keys
import org.web3j.crypto.WalletUtils
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.protocol.exceptions.TransactionException
import java.math.BigDecimal
import java.math.BigInteger
import java.time.LocalDateTime
import javax.persistence.LockModeType

@Service
class AssetWithdrawService(
    private val assetRepository:AssetRepository,
    private val walletRepository:WalletRepository,
    private val transactionRepository: TransactionRepository,
    private val assetHistoryRepository: AssetHistoryRepository,
    private val pushService: PushService
) {
    val log = withdrawLogger()
    val objectMapper = ObjectMapper()

    fun generateWithdrawTxId(userId:Long, assetId:Long) : String {
        return "WITHDRAW_USERID_${userId}_ASSETID_${assetId}"
    }

    @SoftTransactional
    fun doWithdrawAsset(isSucceed:Boolean, transactionReceipt:TransactionReceipt, asset:Asset, ex:Exception?) {
        val mintedTxId = generateWithdrawTxId(asset.owner.id!!, asset.id!!)

        transactionRepository.findByIdOrNull(mintedTxId)?.apply {
            if (isSucceed && transactionReceipt.isStatusOK) {
                log.info("withdrawed succeed:: ${id} , ${transactionReceipt.status}")

                assetRepository.findByIdOrNull(asset.id!!)?.apply {
                    mintDttm = LocalDateTime.now()
                    status = AssetStatusEnum.WITHDRAW
                }

                orgTx = transactionReceipt.transactionHash
                blockNum = transactionReceipt.blockNumber
                status = TransactionStatusEnum.COMPLETED
                confirm = 4
                createdDttm = LocalDateTime.now()
                completedDttm = LocalDateTime.now()

                pushService.send(
                    title = "NFT출금완료",
                    msg = "NFT출금 (${asset.name}) 완료되었습니다 ",
                    userId = userId)

                AssetHistory(assetId = assetId, status = AssetHistoryStatusEnum.WITHDRAW, from = asset.owner, toAddress = toAddress).run {
                    assetHistoryRepository.save(this)
                }
            } else {
                log.info("withdrawed failed :: ${id} , ${transactionReceipt.status}")
                if (status == TransactionStatusEnum.PROCESS) {
                    if(ex != null
                        && ex.cause is TransactionException
                        && ex.message!!.contains("Transaction receipt was not generated after 600 seconds for transaction")
                        && retryCnt <= CONTRACT_CALL_RETRY_MAX_CNT
                    ) {
                        assetRepository.findByIdOrNull(asset.id!!)?.apply {
                            status = AssetStatusEnum.WITHDRAWING
                        }

                        status = TransactionStatusEnum.PENDING
                        createdDttm = LocalDateTime.now()
                        completedDttm = null
                        revertReason = ex.message
                        retryCnt += 1
                    } else {
                        assetRepository.findByIdOrNull(asset.id!!)?.apply {
                            status = AssetStatusEnum.PLACED
                        }

                        //출금 수수료 환불.
                        val wallet = walletRepository.findById(userId).orElseThrow { throw WebException(CodeEnum.NOT_EXIST_WALLET) }
                        wallet.availableGem += fee.toLong()

                        status = TransactionStatusEnum.CANCEL
                        confirm = 0
                        createdDttm = LocalDateTime.now()
                        completedDttm = LocalDateTime.now()
                        revertReason = ex?.message
                    }
                }
            }
        }
    }


    @SoftTransactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun regWithdraw(user:User, reqWithdrawAssetDTO: AssetDTO.ReqWithdrawAssetDTO) : Transaction {
        //출금 주소가 올바른 주소인지 확인.
        if (!WalletUtils.isValidAddress(reqWithdrawAssetDTO.toAddress)) {
            throw WebException(CodeEnum.INVALID_ADDRESS)
        }

        //출금 수수료 확인 및 처리.
        val wallet = walletRepository.findById(user.id!!).orElseThrow { throw WebException(CodeEnum.NOT_EXIST_WALLET) }
        if (wallet.availableGem < WITHDRAW_FEE) {
            throw WebException(CodeEnum.NOT_ENOUGH_WITHDRAW_FEE)
        }

        //내 지갑주소로 출금되는것 방지.
        if (Keys.toChecksumAddress(wallet.address) == Keys.toChecksumAddress(reqWithdrawAssetDTO.toAddress)) {
            throw WebException(CodeEnum.DO_NOT_WITHDRAW_MY_ADDRESS)
        }
        wallet.availableGem -= WITHDRAW_FEE

        //NFT 처리.
        val asset = assetRepository.findByIdAndOwner(reqWithdrawAssetDTO.assetId, user) ?: throw WebException(CodeEnum.NOT_EXIST_ASSET)
        if (asset.status != AssetStatusEnum.PLACED) {
            throw WebException(CodeEnum.INVALID_WITHDRAW_STATUS_ASSET)
        }
        asset.status = AssetStatusEnum.WITHDRAWING

        //거래내역 처리.
        return  Transaction(
            id = generateWithdrawTxId(user.id!!, reqWithdrawAssetDTO.assetId),
            blockNum = BigInteger.ZERO,
            userId = user.id!!,
            coin = CoinEnum.KIMSEA,
            category = TransactionCategoryEnum.WITHDRAW,
            status = TransactionStatusEnum.PENDING,
            assetId = reqWithdrawAssetDTO.assetId,
            fromAddress = wallet.address,
            toAddress = Keys.toChecksumAddress(reqWithdrawAssetDTO.toAddress),
            amount = BigDecimal.ONE,
            gemAmount = 0,
            fee = WITHDRAW_FEE.toBigDecimal(),
            confirm = 0,
            createdDttm = LocalDateTime.now(),
            regDttm = LocalDateTime.now()
        ).run {
            transactionRepository.save(this)
        }
    }
}