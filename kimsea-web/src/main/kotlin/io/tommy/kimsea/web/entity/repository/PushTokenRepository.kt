package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.PushToken
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PushTokenRepository : CrudRepository<PushToken, String> {
    fun findAllByDeviceAndTokenIsNotNull(device:String, pageable: Pageable) : List<PushToken>

    @Query("select count(u.token) from PushToken u where u.device = :device")
    fun countByDevice(device:String):Long
}