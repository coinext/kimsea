package io.tommy.kimsea.web.services.ethereum

import com.fasterxml.jackson.databind.ObjectMapper
import io.tommy.kimsea.web.utils.depositLogger
import io.tommy.kimsea.web.utils.secure.KeyGenUtil
import org.springframework.stereotype.Service
import org.web3j.crypto.Keys
import org.web3j.crypto.WalletFile
import org.web3j.utils.Numeric


@Service
class EthereumService() {
    val log = depositLogger()

    val objectMapper = ObjectMapper()

    data class CreatedWallet(
        val address:String,
        val password:String,
        val walletFile:WalletFile,
        val walletDat:String
    )

    fun create() : CreatedWallet {
        val password: String = KeyGenUtil.generateWalletPassword()

        val walletFile: WalletFile = org.web3j.crypto.Wallet.createStandard(password, Keys.createEcKeyPair())
        val address: String = Numeric.prependHexPrefix(walletFile.address)
        val walletDat: String = objectMapper.writeValueAsString(walletFile)

        return CreatedWallet(
            address,
            password,
            walletFile,
            walletDat
        )
    }
}
