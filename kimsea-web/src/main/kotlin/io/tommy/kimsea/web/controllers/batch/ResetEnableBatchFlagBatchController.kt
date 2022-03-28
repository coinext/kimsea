package io.tommy.kimsea.web.controllers.batch

import io.tommy.kimsea.web.providers.EthereumProvider
import io.tommy.kimsea.web.services.UserService
import io.tommy.kimsea.web.utils.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RestController

@RestController
class ResetEnableBatchFlagBatchController(
    val userService: UserService,
    val ethereumProvider: EthereumProvider
) {

    val log = logger()

    @Scheduled(cron = "0 0 0 * * ?")
    fun doResetEnableBatchFlag() {
        log.info("*doResetEnableBatchFlag begin")
        ethereumProvider.checkEnableService()
        log.info("*doResetEnableBatchFlag end")
    }
}