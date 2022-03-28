package io.tommy.kimsea.web.dto

import io.tommy.kimsea.web.entity.domain.User

class StatDTO {

    data class UserDTO (
        val user:User,
        val totalAssetCnt:Long,
        val totalSellAmount:Long,
        val totalSellPrice:Long,
    )

    data class CreatorDTO (
        val creator:User,
        val totalAssetCnt:Long,
        val totalSellAmount:Long,
        val totalSellPrice:Long,
    )
}