package io.tommy.kimsea.web.handlers

import io.tommy.kimsea.web.exceptions.WebException
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.ModelAndView

@ControllerAdvice(basePackages = ["io.tommy.bluesea.web.controllers"])
@Controller
class ViewExceptionHandler {

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [WebException::class])
    fun handleBaseException(ex: WebException): ModelAndView {
        return ModelAndView("")
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = [Exception::class, RuntimeException::class, Throwable::class])
    fun handleException(ex: Exception): ModelAndView {
        return ModelAndView("")
    }
}