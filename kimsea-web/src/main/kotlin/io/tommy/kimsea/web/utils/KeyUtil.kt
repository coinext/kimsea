package io.tommy.kimsea.web.utils

import org.apache.commons.lang3.RandomStringUtils

class KeyUtil {
    companion object {
        fun genApiKey() : String {
            return RandomStringUtils.randomAlphanumeric(60)
        }
    }
}