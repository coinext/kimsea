package io.tommy.kimsea.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.transaction.annotation.EnableTransactionManagement

@SpringBootApplication(scanBasePackages = ["io.tommy.kimsea.web"])
@EnableJpaRepositories(basePackages = ["io.tommy.kimsea.web.entity.repository"])
@EntityScan("io.tommy.kimsea.web.entity.domain")
@EnableAspectJAutoProxy
@EnableTransactionManagement
@EnableScheduling
@EnableAsync
class WebApplication

fun main(args: Array<String>) {
    runApplication<WebApplication>(*args)
}
