package io.tommy.kimsea.web.controllers.batch

import io.tommy.kimsea.web.configs.Const
import io.tommy.kimsea.web.services.ethereum.EthereumDepositService
import io.tommy.kimsea.web.utils.logger
import org.springframework.core.env.Environment
import org.springframework.core.env.Profiles
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RestController

@RestController
class DepositEthereumBatchController(
    private val ethereumDepositService: EthereumDepositService,
    private val environment: Environment
) {
    val log = logger()

    @Scheduled(fixedDelayString = "10000", initialDelayString = "10000")
    fun consumeDepositTransactions() {
        if (!Const.isEnableBatch) {
            log.info("*isEnableBatch : false")
            return
        }

        ethereumDepositService.consumeDepositTransactions { currentBlockNumber, userId, createdDttm, tx ->
            if (ethereumDepositService.doDeposit(
                    currentBlockNumber,
                    userId,
                    createdDttm,
                    tx
                )) {
                log.info("deposit done.")
            } else {
                log.info("deposit none.")
            }
        }
    }

    @Scheduled(fixedDelayString = "20000", initialDelayString = "10000")
    fun publishDepositTransactions() {
        if (!Const.isEnableBatch) {
            log.info("*isEnableBatch : false")
            return
        }

        if (environment.acceptsProfiles(Profiles.of("local"))) return
       ethereumDepositService.publishDepositTransactions()
    }
}