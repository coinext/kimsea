package io.tommy.kimsea.web.utils

import java.math.BigInteger
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateUtil {
    companion object {
        val FORMATTER_YYYY_MM = DateTimeFormatter.ofPattern("yyyy-MM")
        val FORMATTER_YYYY_MM_DD = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val FORMATTER_HH = DateTimeFormatter.ofPattern("HH")
        val FORMATTER_YYYYMMDD = DateTimeFormatter.ofPattern("yyyyMMdd")
        val FORMATTER_YYYY_MM_DD_HH = DateTimeFormatter.ofPattern("yyyy-MM-dd HH")
        val FORMATTER_YYYY_MM_DD_HH_MM = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        val FORMATTER_YYYY_MM_DD_HH_MM_SS = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        val FORMATTER_HH_MM_SS = DateTimeFormatter.ofPattern("HH:mm:ss")
        val FORMATTER_HH_MM = DateTimeFormatter.ofPattern("HH:mm")
        val FORMATTER_HHMMSSSSS = DateTimeFormatter.ofPattern("HHmmssSSS")
        val FORMATTER_HHMMSS = DateTimeFormatter.ofPattern("HHmmss")
        val FORMATTER_YYYYMMDDHHMMSSSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSS")
        val FORMATTER_YYYYMMDDHHMMSS = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        val FORMATTER_EDDMMMYYYYHHMMSS = DateTimeFormatter.ofPattern("E, dd MMM yyyy HH:mm:ss", Locale.ENGLISH)

        fun timestampToDatetime(timestamp:BigInteger) : LocalDateTime {
            return LocalDateTime.ofInstant(Instant.ofEpochSecond(timestamp.toLong()), TimeZone.getDefault().toZoneId())
        }
    }
}