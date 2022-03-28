package io.tommy.kimsea.web.controllers

import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.enums.AssetStatusEnum
import io.tommy.kimsea.web.enums.CategoryEnum
import io.tommy.kimsea.web.enums.NetworkChainEnum
import io.tommy.kimsea.web.services.PushService
import io.tommy.kimsea.web.utils.logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.CookieValue
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/home")
class HomeController(
    val pushService: PushService,
) {

    val log = logger()

    @GetMapping
    fun home(user: User?,
             @CookieValue(name="ANDROID_PUSH_TOKEN",required = false, defaultValue = "") androidPushToken: String,
             @CookieValue(name="IOS_PUSH_TOKEN", required = false, defaultValue = "") iosPushToken: String) : ModelAndView {
        val mav = ModelAndView("home")
        mav.addObject("networkChains", NetworkChainEnum.values())
        mav.addObject("categories", CategoryEnum.toMap())
        mav.addObject("status", AssetStatusEnum.toMap())

        pushService.regToken(androidPushToken, iosPushToken)
        return mav
    }
}