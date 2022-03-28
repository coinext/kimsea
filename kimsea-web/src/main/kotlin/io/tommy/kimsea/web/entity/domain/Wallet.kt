package io.tommy.kimsea.web.entity.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import io.tommy.kimsea.web.utils.converters.jpa.CipherConverter
import io.tommy.kimsea.web.utils.converters.jpa.CipherWalletDatConverter
import org.web3j.crypto.Credentials
import org.web3j.crypto.WalletFile
import java.time.LocalDateTime
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Wallet (
    @Id
    var userId:Long?,

    var usingGem:Long = 0,
    var availableGem:Long = 0,
    val address:String,

    @JsonIgnore
    @Convert(converter = CipherConverter::class)
    val password:String,

    @JsonIgnore
    @Convert(converter = CipherWalletDatConverter::class)
    val walletDat:WalletFile,

    @Transient
    val contractAddress:String = "",

    val regDttm:LocalDateTime = LocalDateTime.now()
) {
    fun toCredentials() : Credentials {
        val ecKeyPair = org.web3j.crypto.Wallet.decrypt(password, walletDat)
        return Credentials.create(ecKeyPair)
    }
}