package io.tommy.kimsea.web.enums

import java.util.*

enum class SocialTypeEnum(val value: String) {
    FACEBOOK("facebook"), GOOGLE("google"), KAKAO("kakao"), NAVER("naver");

    private val ROLE_PREFIX = "ROLE_"
    val roleType: String
        get() = ROLE_PREFIX + value.uppercase(Locale.getDefault())

    fun isEquals(authority: String): Boolean {
        return roleType == authority
    }
}