package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.Wallet
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface WalletRepository : CrudRepository<Wallet, Long> {

    fun findByAddress(address:String) : Wallet?
    fun existsByAddress(address:String) : Boolean
}