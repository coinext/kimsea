package io.tommy.kimsea.web.providers

import io.tommy.kimsea.web.utils.wrapper.Web3jWrapper
import org.springframework.stereotype.Component
import org.web3j.tx.gas.ContractGasProvider
import org.web3j.tx.gas.DefaultGasProvider
import java.math.BigInteger

@Component
class EthGasProvider(val web3JWrapper: Web3jWrapper) : ContractGasProvider {

    override fun getGasPrice(contractFunc: String?): BigInteger {
        if (contractFunc == "0x") {
            return DefaultGasProvider().gasPrice
        }
        return web3JWrapper.web3j().ethGasPrice().send().gasPrice
    }

    override fun getGasPrice(): BigInteger {
        TODO("Not yet implemented")
    }

    override fun getGasLimit(contractFunc: String?): BigInteger {
        if (contractFunc == "0x") {
            return DefaultGasProvider().gasLimit
        }

        return BigInteger.valueOf(4020000)
        /*
        return web3jWapper.web3j().ethEstimateGas(
            Transaction.createFunctionCallTransaction(null, null, null, null, null, "0x")).send().amountUsed*/
    }

    override fun getGasLimit(): BigInteger {
        TODO("Not yet implemented")
    }
}