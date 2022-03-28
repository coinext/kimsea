package io.tommy.kimsea.web.controllers

import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.enums.CategoryEnum
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/info")
class InfoController {

    @GetMapping("/api")
    fun api(user: User?) : ModelAndView {
        val mav = ModelAndView("api")
        mav.addObject("categories", CategoryEnum.toList())
        return mav
    }

    @GetMapping("/privacy")
    fun privacy() : ModelAndView {
        return ModelAndView("privacy")
    }

    @GetMapping("/terms")
    fun terms() : ModelAndView {
        return ModelAndView("terms")
    }

    @GetMapping("/usage")
    fun usage() : ModelAndView {
        return ModelAndView("usage")
    }
}