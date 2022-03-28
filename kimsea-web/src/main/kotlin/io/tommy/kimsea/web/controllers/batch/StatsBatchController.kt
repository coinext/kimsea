package io.tommy.kimsea.web.controllers.batch

import io.tommy.kimsea.web.services.StatsService
import io.tommy.kimsea.web.utils.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RestController
import kotlin.system.measureTimeMillis

@RestController
class StatsBatchController(
    val statsService: StatsService
) {

    val log = logger()

    @Scheduled(fixedDelayString = "60000", initialDelayString = "0")
    fun doStatus() {
        val elapseTimeMs = measureTimeMillis {
            log.info("*doStatus begin")
            statsService.doStatus()
            log.info("*doStatus end")
        }
        log.info("doStatus elapseTimeMs : ${elapseTimeMs.toInt()}")
    }
}