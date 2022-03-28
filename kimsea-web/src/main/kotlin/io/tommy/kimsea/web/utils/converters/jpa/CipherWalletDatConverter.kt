package io.tommy.kimsea.web.utils.converters.jpa

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import io.tommy.kimsea.web.utils.logger
import io.tommy.kimsea.web.utils.secure.CipherUtil
import org.web3j.crypto.WalletFile
import javax.persistence.AttributeConverter

class CipherWalletDatConverter(
    private val cipherUtil: CipherUtil
) : AttributeConverter<WalletFile, String> {

    val objectMapper = ObjectMapper()

    val log = logger()
    override fun convertToDatabaseColumn(walletFile: WalletFile): String {
        return cipherUtil.encrypt(objectMapper.writeValueAsString(walletFile))
    }

    override fun convertToEntityAttribute(dbData: String): WalletFile {
        return objectMapper.readValue(cipherUtil.decrypt(dbData), object : TypeReference<WalletFile>() {})
    }

}