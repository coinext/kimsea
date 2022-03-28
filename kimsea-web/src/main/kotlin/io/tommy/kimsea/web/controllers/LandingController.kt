package io.tommy.kimsea.web.controllers

import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.enums.CategoryEnum
import io.tommy.kimsea.web.services.asset.AssetService
import io.tommy.kimsea.web.utils.logger
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/")
class LandingController(
    private val assetService: AssetService
) {

    val log = logger()

    @GetMapping("/")
    fun landing(user: User?) : ModelAndView {
        val mav = ModelAndView("landing")
        mav.addObject("categories", CategoryEnum.toList())
        mav.addObject("statistics", assetService.getTopAssetsStatistics())
        return mav
    }

    @GetMapping("/login")
    fun login() : ModelAndView {
        return ModelAndView("login")
    }
}