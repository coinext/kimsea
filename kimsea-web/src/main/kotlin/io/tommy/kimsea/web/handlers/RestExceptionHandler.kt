package io.tommy.kimsea.web.handlers

import io.tommy.kimsea.web.dto.Response
import io.tommy.kimsea.web.enums.CodeEnum
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.services.TelegramAlaramService
import io.tommy.kimsea.web.utils.logger
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice(basePackages = ["io.tommy.bluesea.web.controllers.rest"])
@RestController
class RestExceptionHandler(
    val telegramAlaramService: TelegramAlaramService
) {

    val log = logger()

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [WebException::class])
    fun handleBaseException(ex: WebException): Response<String> {
        log.error("handleBaseException : ${ex.message}")
        telegramAlaramService.sendMessage(ex.codeEnum.getCode() + ":" + ex.message!!)
        return Response(ex.codeEnum)
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class, RuntimeException::class, Throwable::class])
    fun handleException(ex: Exception): Response<String> {
        log.error(ex.toString() + ", " + ex.message)
        telegramAlaramService.sendMessage(ex.message!!)
        return Response(CodeEnum.FAIL, if (ex.message == null) CodeEnum.FAIL.message else ex.message!!)
    }
}