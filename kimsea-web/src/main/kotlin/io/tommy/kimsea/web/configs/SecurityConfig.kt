package io.tommy.kimsea.web.configs

import io.tommy.kimsea.web.dto.OAuthUser
import io.tommy.kimsea.web.enums.CustomOAuth2ProviderEnum
import io.tommy.kimsea.web.services.UserService
import io.tommy.kimsea.web.utils.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.ParameterizedTypeReference
import org.springframework.core.convert.converter.Converter
import org.springframework.http.RequestEntity
import org.springframework.http.ResponseEntity
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler
import org.springframework.security.oauth2.client.registration.ClientRegistration
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequestEntityConverter
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.OAuth2AuthorizationException
import org.springframework.security.oauth2.core.OAuth2Error
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.oauth2.core.user.OAuth2UserAuthority
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint
import org.springframework.util.Assert
import org.springframework.util.StringUtils
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestOperations
import org.springframework.web.client.RestTemplate

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(
    val userService: UserService
) : WebSecurityConfigurerAdapter() {

    @Throws(java.lang.Exception::class)
    override fun configure(web: WebSecurity) {
        web.ignoring().antMatchers(
            "/static/**",
            "/resource/**",
            "/js/**"
        )
    }

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .headers()
            .frameOptions().disable()
            .and()
                .authorizeRequests()
                .antMatchers(
                    "/_health_check",
                    "/",
                    "/oauth2/**",
                    "/login/**",
                    "/batch/**",
                    "/api/**",
                    "/file/**"
                )
                .permitAll()
            .and()
                .oauth2Login()
                .userInfoEndpoint()
                .userService(SecurityBeanConfig.CustomOAuth2UserService(userService)) // 네이버 USER INFO의 응답을 처리하기 위한 설정
            .and()
                .defaultSuccessUrl("/home#/assets/ALL")
                .failureUrl("/")
            .and()
                .exceptionHandling()
                .authenticationEntryPoint(LoginUrlAuthenticationEntryPoint ("/login"))
            .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll()
            .and()
                .csrf().disable()
    }
}


@Configuration
class SecurityBeanConfig() {
    @Bean
    fun clientRegistrationRepository(
        oAuth2ClientProperties: OAuth2ClientProperties,
        @Value("\${custom.oauth2.kakao.client-id}") kakaoClientId: String?,
        @Value("\${custom.oauth2.kakao.client-secret}") kakaoClientSecret: String?,
        @Value("\${custom.oauth2.naver.client-id}") naverClientId: String?,
        @Value("\${custom.oauth2.naver.client-secret}") naverClientSecret: String?
    ): ClientRegistrationRepository {

        val registrations = oAuth2ClientProperties.registration.keys.mapNotNull { client ->
            getRegistration(oAuth2ClientProperties, client)
        }.toMutableList()

        registrations.add(
            CustomOAuth2ProviderEnum.KAKAO.getBuilder("kakao")!!
                .clientId(kakaoClientId)
                .clientSecret(kakaoClientSecret)
                .jwkSetUri("temp").build()
        )
        registrations.add(
            CustomOAuth2ProviderEnum.NAVER.getBuilder("naver")!!
                .clientId(naverClientId)
                .clientSecret(naverClientSecret)
                .jwkSetUri("temp").build()
        )
        return InMemoryClientRegistrationRepository(registrations)
    }

    private fun getRegistration(clientProperties: OAuth2ClientProperties, client: String): ClientRegistration? {
        if ("google" == client) {
            val registration: OAuth2ClientProperties.Registration? = clientProperties.registration["google"]
            return CommonOAuth2Provider.GOOGLE.getBuilder(client)
                .clientId(registration!!.clientId)
                .clientSecret(registration.clientSecret)
                .scope("email", "profile")
                .build()
        }
        if ("facebook" == client) {
            val registration: OAuth2ClientProperties.Registration? = clientProperties.registration["facebook"]
            return CommonOAuth2Provider.FACEBOOK.getBuilder(client)
                .clientId(registration!!.clientId)
                .clientSecret(registration.clientSecret)
                .userInfoUri("https://graph.facebook.com/me?fields=id,name,email,link")
                .scope("email", "public_profile")
                .build()
        }
        return null
    }

    class CustomOAuth2UserService(
        val userService: UserService
    ) : DefaultOAuth2UserService() {
        private val requestEntityConverter: Converter<OAuth2UserRequest, RequestEntity<*>> =
            OAuth2UserRequestEntityConverter()
        private val restOperations: RestOperations

        val log = logger()

        companion object {
            private const val MISSING_USER_INFO_URI_ERROR_CODE = "missing_user_info_uri"
            private const val MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE = "missing_user_name_attribute"
            private const val INVALID_USER_INFO_RESPONSE_ERROR_CODE = "invalid_user_info_response"
            private val PARAMETERIZED_RESPONSE_TYPE: ParameterizedTypeReference<Map<String?, Any?>?> =
                object : ParameterizedTypeReference<Map<String?, Any?>?>() {}
        }

        init {
            val restTemplate = RestTemplate()
            restTemplate.errorHandler = OAuth2ErrorResponseErrorHandler()
            this.restOperations = restTemplate
        }

        @Throws(OAuth2AuthenticationException::class)
        override fun loadUser(userRequest: OAuth2UserRequest): OAuth2User {
            Assert.notNull(
                userRequest,
                "userRequest cannot be null"
            )

            if (!StringUtils.hasText(
                    userRequest.clientRegistration.providerDetails.userInfoEndpoint.uri
                )
            ) {
                val oauth2Error = OAuth2Error(
                    MISSING_USER_INFO_URI_ERROR_CODE,
                    "Missing required UserInfo Uri in UserInfoEndpoint for Client Registration: " + userRequest.clientRegistration
                        .registrationId,
                    null
                )
                throw OAuth2AuthenticationException(oauth2Error, oauth2Error.toString())
            }

            val userNameAttributeName = userRequest.clientRegistration.providerDetails.userInfoEndpoint
                .userNameAttributeName
            if (!StringUtils.hasText(userNameAttributeName)) {
                val oauth2Error = OAuth2Error(
                    MISSING_USER_NAME_ATTRIBUTE_ERROR_CODE,
                    "Missing required \"user name\" attribute name in UserInfoEndpoint for Client Registration: " + userRequest.clientRegistration
                        .registrationId,
                    null
                )
                throw OAuth2AuthenticationException(oauth2Error, oauth2Error.toString())
            }

            val request = this.requestEntityConverter.convert(userRequest)!!
            val response: ResponseEntity<Map<String?, Any?>?>
            try {
                response = this.restOperations.exchange(request, PARAMETERIZED_RESPONSE_TYPE)
            } catch (ex: OAuth2AuthorizationException) {
                var oauth2Error = ex.error
                val errorDetails = StringBuilder()
                errorDetails.append(
                    "Error details: ["
                )
                errorDetails.append("UserInfo Uri: ").append(
                    userRequest.clientRegistration.providerDetails.userInfoEndpoint.uri
                )
                errorDetails.append(", Error Code: ")
                    .append(oauth2Error.errorCode)
                if (oauth2Error.description != null) {
                    errorDetails.append(", Error Description: ")
                        .append(oauth2Error.description)
                }
                errorDetails.append("]")
                oauth2Error = OAuth2Error(
                    INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: $errorDetails",
                    null
                )
                throw OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex)
            } catch (ex: RestClientException) {
                val oauth2Error = OAuth2Error(
                    INVALID_USER_INFO_RESPONSE_ERROR_CODE,
                    "An error occurred while attempting to retrieve the UserInfo Resource: " + ex.message,
                    null
                )
                throw OAuth2AuthenticationException(oauth2Error, oauth2Error.toString(), ex)
            }
            val userAttributes: Map<String, Any> = getUserAttributes(response)
            val authorities: MutableSet<GrantedAuthority> = LinkedHashSet()
            authorities.add(
                OAuth2UserAuthority(userAttributes)
            )
            val token = userRequest.accessToken
            for (authority in token.scopes) {
                authorities.add(
                    SimpleGrantedAuthority("SCOPE_$authority")
                )
            }

            val user = userService.register(userRequest, userAttributes)
            return OAuthUser(authorities, userAttributes, userNameAttributeName, user)
        }

        fun getUserAttributes(response: ResponseEntity<Map<String?, Any?>?>):Map<String, Any>  {
            val userAttributes = response.body as LinkedHashMap<String, Any>
            if(userAttributes.containsKey("response")) {
                val responseData = userAttributes["response"] as LinkedHashMap<String, Any>
                userAttributes.putAll(responseData)
                userAttributes.remove("response")
            }
            return userAttributes
        }
    }
}