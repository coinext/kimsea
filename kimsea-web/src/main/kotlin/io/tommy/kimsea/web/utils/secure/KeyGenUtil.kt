package io.tommy.kimsea.web.utils.secure

import org.apache.commons.lang3.RandomStringUtils

class KeyGenUtil {

    companion object {
        fun generateWalletPassword() : String {
            return RandomStringUtils.randomAlphanumeric(64)
        }
    }
}