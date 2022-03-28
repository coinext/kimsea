package io.tommy.kimsea.web.entity.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class PushToken (
    @Id
    val token: String,
    val device:String,
    val regDttm:LocalDateTime
)
