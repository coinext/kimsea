package io.tommy.kimsea.web.controllers.batch

import io.tommy.kimsea.web.services.UserService
import io.tommy.kimsea.web.utils.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RestController

@RestController
class ResetApiQuataBatchController(
    val userService: UserService
) {

    val log = logger()

    @Scheduled(cron = "0 0 0 * * ?")
    fun doResetApiQuata() {
        log.info("*doResetApiQuata begin")
        userService.resetQuata()
        log.info("*doStatus end")
    }
}