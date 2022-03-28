package io.tommy.kimsea.web.dto

import io.tommy.kimsea.web.entity.domain.User
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.oauth2.core.user.DefaultOAuth2User

class OAuthUser(
    authorities: Collection<GrantedAuthority?>?,
    attributes: Map<String, Any>,
    nameAttributeKey: String?,
    val user:User
) : DefaultOAuth2User(authorities, attributes, nameAttributeKey) {

}