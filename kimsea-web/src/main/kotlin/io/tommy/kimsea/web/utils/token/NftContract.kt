package io.tommy.kimsea.web.utils.token

import io.tommy.kimsea.web.entity.domain.Wallet
import io.tommy.kimsea.web.enums.CodeEnum
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.utils.logger
import io.tommy.kimsea.web.utils.wrapper.Web3jWrapper
import org.web3j.abi.FunctionEncoder
import org.web3j.abi.datatypes.*
import org.web3j.abi.datatypes.Function
import org.web3j.abi.datatypes.generated.Uint256
import org.web3j.contracts.eip721.generated.ERC721
import org.web3j.contracts.eip721.generated.ERC721Metadata
import org.web3j.crypto.Hash
import org.web3j.crypto.RawTransaction
import org.web3j.crypto.TransactionEncoder
import org.web3j.protocol.core.DefaultBlockParameterName
import org.web3j.protocol.core.RemoteCall
import org.web3j.protocol.core.methods.response.TransactionReceipt
import org.web3j.tx.gas.ContractGasProvider
import org.web3j.utils.Numeric
import java.io.IOException
import java.math.BigInteger
import java.util.*

class NftContract(
    private val web3JWrapper: Web3jWrapper,
    private val wallet: Wallet,
    private val contractGasProvider: ContractGasProvider
) : ERC721(wallet.contractAddress, web3JWrapper.web3j(), wallet.toCredentials(), contractGasProvider) {

    val log = logger()

    fun getTokenURI(tokenId:Long) : RemoteCall<String> {
        return object : ERC721Metadata(wallet.contractAddress, web3JWrapper.web3j(), wallet.toCredentials(), contractGasProvider) {}.tokenURI(tokenId.toBigInteger())
    }

    fun deploy(
        binary:String
    ): RemoteCall<NftContract> {
        val encodedConstructor = FunctionEncoder.encodeConstructor(Arrays.asList())
        return deployRemoteCall(
            NftContract::class.java,
            web3JWrapper.web3j(),
            wallet.toCredentials(),
            contractGasProvider,
            binary,
            encodedConstructor
        )
    }

    fun getMultupleMintAndSendPreTxId(
        toAddresses: List<String>,
        tokenIds: List<BigInteger>,
        uris: List<String>,
    ): String {
        var ethGetTransactionCount = try {
            web3JWrapper.web3j().ethGetTransactionCount(
                wallet.address, DefaultBlockParameterName.PENDING
            ).send()
        } catch (e: IOException) {
            throw WebException(CodeEnum.FAIL)
        }

        val nonce = ethGetTransactionCount.transactionCount
        log.info("getMultupleMintAndSendPreTxId Nonce ====>>>>>> $nonce")
        val encodedFunction = FunctionEncoder.encode(FuncMultupleMintAndSend(toAddresses, tokenIds, uris))

        val rawTransaction = RawTransaction.createTransaction(
            nonce,
            gasProvider.getGasPrice(encodedFunction),
            gasProvider.getGasLimit(encodedFunction),
            contractAddress,
            BigInteger.ZERO, encodedFunction
        )
        val signedMessage = TransactionEncoder.signMessage(rawTransaction, wallet.toCredentials())
        val hexValue = Numeric.toHexString(signedMessage)
        return Hash.sha3(hexValue)
    }

    fun FuncMultupleMintAndSend(
            toAddresses: List<String>,
            tokenIds: List<BigInteger>,
            uris: List<String>,) : Function{
        val inputParameters: MutableList<Type<*>> = ArrayList<Type<*>>().apply {
            add(DynamicArray(Address::class.java, toAddresses.map { r -> Address(r) }.toList()))
            add(DynamicArray(Uint256::class.java, tokenIds.map{ r -> Uint256(r) }.toList()))
            add(DynamicArray(Utf8String::class.java, uris.map{ r -> Utf8String(r) }.toList()))
        }

        return Function(
            "multipleMintAndSend",
            inputParameters, emptyList()
        )
    }

    fun multipleMintAndSend(
        toAddresses: List<String>,
        tokenIds: List<BigInteger>,
        uris: List<String>,
    ): RemoteCall<TransactionReceipt> {
        return executeRemoteCallTransaction(FuncMultupleMintAndSend(toAddresses, tokenIds, uris))
    }
}