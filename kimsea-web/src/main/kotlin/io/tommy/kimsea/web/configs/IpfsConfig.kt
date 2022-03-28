package io.tommy.kimsea.web.configs

import io.ipfs.api.IPFS
import io.tommy.kimsea.web.utils.logger
import io.tommy.kimsea.web.utils.wrapper.IpfsWrapper
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy


@Configuration
class IpfsConfig(
    @Value("\${ipfs.network.rpc_urls}")
    val IPFS_NETWORK_RPC_URLS:List<String>,
) {

    val log = logger()

    @Lazy
    @Bean
    fun ipfs() : IPFS {
        if (IPFS_NETWORK_RPC_URLS[0] == "ipfs.infura.io") {
            return IPFS(IPFS_NETWORK_RPC_URLS[0], 5001, "/api/v0/", true)
        } else {
            return IPFS(IPFS_NETWORK_RPC_URLS[0], 5001, "/api/v0/", false)
        }

    }

    @Lazy
    @Bean
    fun ipfsWapper(): IpfsWrapper {
        return IpfsWrapper(IPFS_NETWORK_RPC_URLS).attached()
    }
}