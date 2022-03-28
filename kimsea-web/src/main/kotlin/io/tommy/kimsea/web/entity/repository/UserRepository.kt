package io.tommy.kimsea.web.entity.repository

import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.enums.SocialTypeEnum
import org.springframework.data.domain.Pageable
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository : CrudRepository<User, Long> {
    fun findOneByIdAndDeviceAndPushTokenIsNotNull(id:Long, device:String):User
    fun findAllByDeviceAndPushTokenIsNotNull(device:String, pageable: Pageable):List<User>

    fun findByApiKey(apiKey:String) : User?
    fun findByUidAndSocialType(uid:String, socialType: SocialTypeEnum) : User?
    fun findAll(pageable: Pageable) : List<User>
}