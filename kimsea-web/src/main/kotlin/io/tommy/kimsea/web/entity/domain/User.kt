package io.tommy.kimsea.web.entity.domain

import com.fasterxml.jackson.annotation.JsonIgnore
import io.tommy.kimsea.web.enums.SocialTypeEnum
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class User (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id:Long?,

    var uid:String,

    @Enumerated(EnumType.STRING)
    var socialType: SocialTypeEnum,
    var profile:String,

    @JsonIgnore
    var email:String,
    var profileImgUrl:String,

    @JsonIgnore
    var apiKey:String? = null,

    var enableApi:String = "N",

    @JsonIgnore
    var accessToken:String,

    @JsonIgnore
    var tokenExpiresAt:LocalDateTime,

    @JsonIgnore
    var pushToken:String? = null,

    @JsonIgnore
    var device:String? = null,

    @JsonIgnore
    var regDttm:LocalDateTime
)