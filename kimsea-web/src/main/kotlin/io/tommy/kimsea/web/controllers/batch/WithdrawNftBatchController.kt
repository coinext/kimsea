package io.tommy.kimsea.web.controllers.batch

import io.tommy.kimsea.web.configs.Const
import io.tommy.kimsea.web.services.asset.AssetWithdrawService
import io.tommy.kimsea.web.services.ethereum.EthereumWithdrawService
import io.tommy.kimsea.web.utils.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RestController

@RestController
class WithdrawNftBatchController(
    private val ethereumWithdrawService: EthereumWithdrawService,
    private val assetWithdrawService: AssetWithdrawService
) {

    val log = logger()

    @Scheduled(fixedDelayString = "10000", initialDelayString = "10000")
    fun consumeWithdrawTransactions() {
        if (!Const.isEnableBatch) {
            log.info("*isEnableBatch : false")
            return
        }

        ethereumWithdrawService.consumeWithdrawTransactions { isSucceed, transactionReceipt, assets, ex ->
            log.info("== consumeWithdrawTransactions")
            assets.forEach {asset ->
                assetWithdrawService.doWithdrawAsset(isSucceed, transactionReceipt, asset, ex)
            }
        }
    }

    @Scheduled(fixedDelayString = "20000", initialDelayString = "10000")
    fun publishWithdrawTransactions() {
        if (!Const.isEnableBatch) {
            log.info("*isEnableBatch : false")
            return
        }

        log.info("== publishWithdrawTransactions")
        ethereumWithdrawService.publishWithdrawTransactions()
    }
}