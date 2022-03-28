package io.tommy.kimsea.web.controllers.advices

import io.tommy.kimsea.web.dto.OAuthUser
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.utils.logger
import org.springframework.core.annotation.Order
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ModelAttribute

@ControllerAdvice(basePackages = ["io.tommy.bluesea.web.controllers"])
@Order(1)
class ControllerAdvice() {
    val log = logger()

    @ModelAttribute("user")
    fun getCurrentUser(@AuthenticationPrincipal oAuth2User: OAuth2User?): User? {
        return if (oAuth2User == null) null else (oAuth2User as OAuthUser).user
    }

    @ModelAttribute("isLogin")
    fun isLogin(@AuthenticationPrincipal oAuth2User: OAuth2User?): Boolean {
        return oAuth2User != null
    }
}