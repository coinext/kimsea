package io.tommy.kimsea.web.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline fun <reified T> T.logger(): Logger {
    return LoggerFactory.getLogger(T::class.java)
}

inline fun <reified T> T.logger(name:String): Logger {
    return LoggerFactory.getLogger(name)
}

inline fun <reified T> T.depositLogger(): Logger {
    return LoggerFactory.getLogger("DEPOSIT")
}

inline fun <reified T> T.withdrawLogger(): Logger {
    return LoggerFactory.getLogger("WITHDRAW")
}

inline fun <reified T> T.bidLogger(): Logger {
    return LoggerFactory.getLogger("BID")
}
