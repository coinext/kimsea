package io.tommy.kimsea.web.services

import io.tommy.kimsea.web.annotations.SoftTransactional
import io.tommy.kimsea.web.entity.domain.AdminWallet
import io.tommy.kimsea.web.entity.domain.Wallet
import io.tommy.kimsea.web.entity.repository.AdminWalletRepository
import io.tommy.kimsea.web.entity.repository.WalletRepository
import io.tommy.kimsea.web.utils.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import java.util.concurrent.ConcurrentHashMap
import javax.persistence.LockModeType

@Service
class WalletService(
    private val walletRepository: WalletRepository,
    private val adminWalletRepository: AdminWalletRepository,
    @Value("\${spring.profiles.active}")
    private val ACTIVE_PROFILE:String,
    @Value("\${admin.user.id}")
    private val ADMIN_USER_ID:Long,
) {
    val log = logger()
    var cachedAddressIndexedWallets = ConcurrentHashMap<String, Boolean>(100000)



    fun getAdminWallet() : AdminWallet {
        return adminWalletRepository.findByUserIdAndEnv(ADMIN_USER_ID, ACTIVE_PROFILE)
    }

    fun getAdminAddress() : String {
        return getAdminWallet().address
    }

    fun isDepositedWalletByAddress(address: String) : Boolean {
        val isExist = cachedAddressIndexedWallets[address] ?: walletRepository.existsByAddress(address)
        if (isExist) {
            cachedAddressIndexedWallets.put(address, true)
            return true
        }
        return false
    }

    fun getWalletByAddress(address:String) : Wallet? {
        return walletRepository.findByAddress(address)
    }

    fun getWalletByUserId(userId:Long) : Wallet {
        return walletRepository.findByIdOrNull(userId)!!
    }

    @SoftTransactional
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    fun transferToGem(userId:Long, gemAmount:Int) {
        walletRepository.findByIdOrNull(userId)?.apply {
            availableGem += gemAmount
        }
    }
}