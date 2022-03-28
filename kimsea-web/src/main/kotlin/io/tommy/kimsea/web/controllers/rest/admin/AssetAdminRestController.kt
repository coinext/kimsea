package io.tommy.kimsea.web.controllers.rest.admin

import io.tommy.kimsea.web.configs.Const.Companion.NFT_CONTRACT_BINARY
import io.tommy.kimsea.web.entity.repository.AssetRepository
import io.tommy.kimsea.web.entity.repository.TransactionRepository
import io.tommy.kimsea.web.enums.CodeEnum
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.providers.EthereumProvider
import io.tommy.kimsea.web.services.PushService
import io.tommy.kimsea.web.services.WalletService
import io.tommy.kimsea.web.utils.logger
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/admin/api/v1/")
class AssetAdminRestController(
    val ethereumProvider: EthereumProvider,
    val assetRepository: AssetRepository,
    val transactionRepository: TransactionRepository,
    val walletService:WalletService,
    val pushService: PushService,
    val environment:Environment
) {
    val log = logger()

    fun isDev() {
        if (!environment.acceptsProfiles(Profiles.of("local"))) throw WebException(CodeEnum.FAIL)
    }

    @GetMapping("/createAsset")
    fun createAsset() : String {
        isDev()
        log.info("createAsset")
        ethereumProvider.createNftToken(walletService.getAdminWallet().toWallet(), NFT_CONTRACT_BINARY) { nftContract ->
            log.info("createNftToken succeed. ${nftContract.contractAddress}")
        }
        return "ok"
    }

    @GetMapping("/mintAndSendNftToken")
    fun mintAndSendNftToken() : String {
        isDev()
        log.info("mintAndSendNftToken")
        ethereumProvider.mintAndSendNftToken(listOf("0xaD15b107D8AB127A9eF111B9F633b3984Be86aaA"), listOf(assetRepository.findById(218).get())) { isSucceed, assets, transactionReceipt, ex  ->
            log.info("mintAndSendNftToken succeed. ${transactionReceipt.transactionHash}")
        }
        return "ok"
    }
}