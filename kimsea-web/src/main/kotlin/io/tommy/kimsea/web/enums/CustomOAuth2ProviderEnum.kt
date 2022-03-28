package io.tommy.kimsea.web.enums

import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.core.AuthorizationGrantType
import org.springframework.security.oauth2.core.ClientAuthenticationMethod


enum class CustomOAuth2ProviderEnum {
    KAKAO {
        override fun getBuilder(registrationId: String?): ClientRegistration.Builder {
            val builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL)
            builder.scope("profile_image", "profile_nickname"/*, "account_email"*/)
            builder.authorizationUri("https://kauth.kakao.com/oauth/authorize")
            builder.tokenUri("https://kauth.kakao.com/oauth/token")
            builder.userInfoUri("https://kapi.kakao.com/v2/user/me")
            builder.userNameAttributeName("id")
            builder.clientName("Kakao")
            return builder
        }
    },
    NAVER {
        override fun getBuilder(registrationId: String?): ClientRegistration.Builder {
            val builder = getBuilder(registrationId, ClientAuthenticationMethod.POST, DEFAULT_LOGIN_REDIRECT_URL)
            builder.scope("profile")
            builder.authorizationUri("https://nid.naver.com/oauth2.0/authorize")
            builder.tokenUri("https://nid.naver.com/oauth2.0/token")
            builder.userInfoUri("https://openapi.naver.com/v1/nid/me")
            builder.userNameAttributeName("id")
            builder.clientName("Naver")
            return builder
        }
    };

    protected fun getBuilder(
        registrationId: String?,
        method: ClientAuthenticationMethod?,
        redirectUri: String?
    ): ClientRegistration.Builder {
        val builder = ClientRegistration.withRegistrationId(registrationId)
        builder.clientAuthenticationMethod(method)
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
        builder.redirectUriTemplate(redirectUri)
        return builder
    }

    abstract fun getBuilder(registrationId: String?): ClientRegistration.Builder?

    companion object {
        private const val DEFAULT_LOGIN_REDIRECT_URL = "{baseUrl}/login/oauth2/code/{registrationId}"
    }
}
