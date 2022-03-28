package io.tommy.kimsea.web.providers

import com.fasterxml.jackson.databind.ObjectMapper
import io.tommy.kimsea.web.configs.Const
import io.tommy.kimsea.web.entity.domain.Asset
import io.tommy.kimsea.web.entity.domain.Wallet
import io.tommy.kimsea.web.enums.CodeEnum
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.services.TelegramAlaramService
import io.tommy.kimsea.web.utils.logger
import io.tommy.kimsea.web.utils.token.NftContract
import io.tommy.kimsea.web.utils.wrapper.Web3jWrapper
import org.springframework.stereotype.Component
import org.web3j.contracts.eip721.generated.ERC721Enumerable
import org.web3j.contracts.eip721.generated.ERC721Metadata
import org.web3j.crypto.Credentials
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.methods.request.Transaction
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.exceptions.ContractCallException
import org.web3j.utils.Numeric
import java.math.BigInteger
import javax.annotation.PostConstruct

@Component
class EthereumProvider(
    val web3JWrapper: Web3jWrapper,
    val nftContract: NftContract,
    val ethGasProvider : EthGasProvider,
    val telegramAlaramService: TelegramAlaramService
) {
    val log = logger()
    val objectMapper = ObjectMapper()

    fun getSignTransaction(rawTransaction: RawTransaction, credentials: Credentials) : String {
        return Numeric.toHexString(TransactionEncoder.signMessage(rawTransaction, credentials))
    }

    @PostConstruct
    fun init() {
        try {
            checkEnableService()
        } catch (ex:Exception) {
            log.error("* checkEnableService disable")
            telegramAlaramService.sendMessage(CodeEnum.FAIL.getCode() + ":" + ex.message!!)
        }
    }

    fun checkEnableService() {
        try {
            web3JWrapper.web3j().ethGasPrice().send().gasPrice
            Const.isEnableBatch = true
        } catch (ex:Exception) {
            Const.isEnableBatch = false
            throw ex
        }
    }

    fun getGasPrice() : BigInteger {
        return web3JWrapper.web3j().ethGasPrice().send().gasPrice
    }

    fun getGasLimitForSendEth(fromAddress:String, toAddress:String, amount: BigInteger) : BigInteger {
        return web3JWrapper.web3j().ethEstimateGas(
            Transaction.createEtherTransaction(fromAddress, null, null, null, toAddress, amount)).send().amountUsed
    }

    fun getGasLimitForCallFunc(fromAddress:String, toAddress:String, data: String) : BigInteger {
        return web3JWrapper.web3j().ethEstimateGas(
            Transaction.createFunctionCallTransaction(fromAddress, null, null, null, toAddress, data)).send().amountUsed
    }

    fun withdrawAll(wallet: Wallet, toAddress:String) {
        web3JWrapper.web3j().ethGetBalance(wallet.address, DefaultBlockParameterName.LATEST).sendAsync().thenAccept { ethBalance->
            val wholeBalance = ethBalance.balance
            withdraw(wallet, toAddress, wholeBalance)
        }.exceptionally { ex->
            throw ex
        }
    }

    private fun withdraw(wallet: Wallet, toAddress:String, amount: BigInteger) {
        web3JWrapper.web3j().ethGetTransactionCount(
            wallet.address, DefaultBlockParameterName.LATEST
        ).sendAsync().thenAccept { transactionReceipt ->

            val gasPrice = getGasPrice()
            val gasLimit = getGasLimitForSendEth(wallet.address, toAddress, amount)
            val sentAmount =  amount.subtract(gasPrice.multiply(gasLimit))

            val signTransaction = RawTransaction.createEtherTransaction(
                transactionReceipt.transactionCount,
                gasPrice,
                gasLimit,
                toAddress,
                sentAmount).run {
                getSignTransaction(this, wallet.toCredentials())
            }

            log.info("SENDING ETHEREUM ::: userId : ${wallet.userId} *gasPrice : $gasPrice  *gasLimit : $gasLimit  *from : ${wallet.address}  *to : $toAddress  *amount : $amount  *sentAmount : $sentAmount")

            web3JWrapper.web3j().ethSendRawTransaction(signTransaction).sendAsync().thenAccept{ tx ->
                if (tx.error != null) {
                    log.info("withdraw failed..... ${tx.transactionHash} ::  ${tx.result} :: ${tx.error.message}")
                } else {
                    log.info("withdraw done..... ${tx.transactionHash} ::  ${tx.result} :: ${tx.error?.message}")
                }
            }.exceptionally {ex ->
                log.error(ex.message)
                throw ex
            }
        }.exceptionally { ex ->
            log.error(ex.message)
            throw ex
        }
    }

    fun getTransactionByTxId(txId:String) : TransactionReceipt {
        return web3JWrapper.web3j().ethGetTransactionReceipt(txId).send().transactionReceipt.orElseThrow { throw WebException(CodeEnum.NOT_EXIST_TRANSACTION_RECEIPT) }
    }

    fun getTotalSupply(wallet: Wallet, contractAddress:String) : BigInteger {
        return object : ERC721Enumerable(contractAddress, web3JWrapper.web3j(), wallet.toCredentials(), ethGasProvider) {}.totalSupply().send()
    }

    fun getTokenURI(tokenId:Long) : String {
        return nftContract.getTokenURI(tokenId).send()
    }

    fun isMintedAsset(tokenId:Long) : Boolean {
        try {
            nftContract.getTokenURI(tokenId).send()
            return true
        } catch (ex: ContractCallException) {
        } catch (ex:Exception) {
        }
        return false
    }

    fun availableAsset(tokenId:Long) {
        try {
            nftContract.getTokenURI(tokenId).send()
            throw WebException(CodeEnum.DO_NOT_TRADE_WITHDRAW_ASSET)
        } catch (ex: ContractCallException) {
        } catch (ex:Exception) {
            throw WebException(CodeEnum.NOT_AVAILABLE_SERVICE)
        }
    }

    fun getName(wallet: Wallet, contractAddress:String) : String {
        return object : ERC721Metadata(contractAddress, web3JWrapper.web3j(), wallet.toCredentials(), ethGasProvider) {}.name().send()
    }

    fun createNftToken(wallet: Wallet, contractBinary:String, consumer: (NftContract) -> Unit) : String {
        nftContract.deploy(contractBinary).sendAsync()
            .thenAccept {
                consumer(it)
            }
            .exceptionally { ex ->
                log.error(ex.message)
                throw ex
            }
        return ""
    }

    fun getMintAndSendNftTokenPreTxId(toAddresses:List<String>, assets:List<Asset>) : String {
        return nftContract
            .getMultupleMintAndSendPreTxId(
                toAddresses,
                assets.map { asset -> asset.id!!.toBigInteger() }.toList(),
            assets.map { asset -> asset.metadataUrl!! }.toList())
    }

    fun mintAndSendNftToken(toAddresses:List<String>, assets:List<Asset>, consumer: (Boolean, List<Asset>, TransactionReceipt, Exception?) -> Unit) {
        nftContract.multipleMintAndSend(
            toAddresses,
            assets.map { asset -> asset.id!!.toBigInteger() }.toList(),
            assets.map { asset -> asset.metadataUrl!! }.toList()
        ).sendAsync().thenAccept {
            consumer(true, assets, it, null)
        }.exceptionally { ex->
            consumer(false, assets, TransactionReceipt(), ex as Exception)
            throw ex
        }
    }
}