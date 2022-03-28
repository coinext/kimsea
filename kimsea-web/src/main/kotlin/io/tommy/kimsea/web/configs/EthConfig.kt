package io.tommy.kimsea.web.configs

import io.tommy.kimsea.web.providers.EthGasProvider
import io.tommy.kimsea.web.services.WalletService
import io.tommy.kimsea.web.utils.token.NftContract
import io.tommy.kimsea.web.utils.wrapper.Web3jWrapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class EthConfig(
    @Value("\${eth.network.rpc_urls}")
    val ETH_NETWORK_RPC_URLS:List<String>,
    val walletService: WalletService,
) {

    @Bean
    fun web3jWapper(): Web3jWrapper {
        return Web3jWrapper(ETH_NETWORK_RPC_URLS).attached()
    }

    @Bean
    fun nftContract(): NftContract {
        val adminWallet = walletService.getAdminWallet()
        return NftContract(web3jWapper(), adminWallet.toWallet(), EthGasProvider(web3jWapper()))
    }
}