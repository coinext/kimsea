package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.AdminWallet
import org.springframework.data.repository.CrudRepository

interface AdminWalletRepository: CrudRepository<AdminWallet, Long> {

    fun findByUserIdAndEnv(userId:Long, env:String) : AdminWallet
}