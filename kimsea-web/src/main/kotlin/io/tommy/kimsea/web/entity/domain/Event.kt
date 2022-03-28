package io.tommy.kimsea.web.entity.domain

import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Event(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long? = null,

    val name:String,
    val content:String,
    val regDttm:LocalDateTime = LocalDateTime.now()
)