package io.tommy.kimsea.web.controllers.batch

import io.tommy.kimsea.web.configs.Const
import io.tommy.kimsea.web.services.bid.BidCompositeService
import io.tommy.kimsea.web.utils.logger
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.web.bind.annotation.RestController
import kotlin.system.measureTimeMillis

@RestController
class BidNftBatchController(
    private val bidCompositeService: BidCompositeService
) {

    val log = logger()

    @Scheduled(fixedDelayString = "60000", initialDelayString = "0")
    fun doBidProcess() {
        if (!Const.isEnableBatch) {
            log.info("*isEnableBatch : false")
            return
        }

        val elapseTimeMs = measureTimeMillis {
            log.info("*doBidProcess begin")
            bidCompositeService.doBid()
            log.info("*doBidProcess end")
        }
        log.info("doBidProcess elapseTimeMs : ${elapseTimeMs.toInt()}")
    }
}