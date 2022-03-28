package io.tommy.kimsea.web.utils.converters.jpa

import io.tommy.kimsea.web.utils.logger
import io.tommy.kimsea.web.utils.secure.CipherUtil
import javax.persistence.AttributeConverter

class CipherConverter(
    private val cipherUtil: CipherUtil
) : AttributeConverter<String, String> {

    val log = logger()

    override fun convertToDatabaseColumn(attribute: String?): String {
        return cipherUtil.encrypt(attribute!!)
    }

    override fun convertToEntityAttribute(dbData: String?): String {
        return cipherUtil.decrypt(dbData!!)
    }
}