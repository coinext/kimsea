package io.tommy.kimsea.web.services

import io.tommy.kimsea.web.annotations.SoftTransactional
import io.tommy.kimsea.web.entity.domain.User
import io.tommy.kimsea.web.entity.domain.Wallet
import io.tommy.kimsea.web.entity.repository.UserRepository
import io.tommy.kimsea.web.entity.repository.WalletRepository
import io.tommy.kimsea.web.enums.CodeEnum
import io.tommy.kimsea.web.enums.SocialTypeEnum
import io.tommy.kimsea.web.exceptions.WebException
import io.tommy.kimsea.web.services.ethereum.EthereumService
import io.tommy.kimsea.web.utils.KeyUtil
import io.tommy.kimsea.web.utils.logger
import io.tommy.kimsea.web.utils.secure.CipherUtil
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.stereotype.Service
import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.atomic.AtomicInteger

@Service
class UserService(
    val userRepository: UserRepository,
    val walletRepository: WalletRepository,
    val ethereumService: EthereumService,
    val cipherUtil: CipherUtil
) {
    val log = logger()
    val API_QUATAS = mutableMapOf<String, AtomicInteger>()

    fun getQuataKey(user:User) : String {
        return "USER_" + user.id
    }

    fun resetQuata() {
        API_QUATAS.clear()
    }

    fun getQuata(user:User) : Int {
        val quata = API_QUATAS.getOrDefault(getQuataKey(user), AtomicInteger(100))
        return quata.get()
    }

    fun validateUserForAPI(user:User?, token:String?) : User {
        var _user = user
        if (user == null && token != null) {
            _user = getUserByApiKey(token)
            if (_user == null) {
                throw WebException(CodeEnum.INVALID_APIKEY)
            }

            if (_user.enableApi != "Y") {
                throw WebException(CodeEnum.DISABLE_API)
            }

            //API QUATA
            val quata = API_QUATAS.getOrDefault(getQuataKey(_user), AtomicInteger(100))
            if (quata.get() <= 1) {
                throw WebException(CodeEnum.EXCEED_LIMIT_USAGE_APIKEY)
            }
            quata.getAndDecrement()

            log.info("*API_QUATA_USER_${_user.id}", quata.get())
            API_QUATAS.put(getQuataKey(_user), quata)
        }
        return _user!!
    }

    fun getUserByApiKey(apiKey:String) : User? {
        return userRepository.findByApiKey(apiKey)
    }

    fun getUserById(userId:Long) : User {
        return userRepository.findById(userId).get()
    }

    @SoftTransactional
    fun register(oAuth2UserRequest: OAuth2UserRequest, userAttributes: Map<String, Any>) : User {
        val socialTypeEnum = SocialTypeEnum.valueOf(oAuth2UserRequest.clientRegistration.clientName.uppercase())

        var _pushToken = ""
        var _device = ""
        val request = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request
        val userAgent = (RequestContextHolder.getRequestAttributes() as ServletRequestAttributes).request.getHeader("User-Agent")
        var isMobileApp = false
        if (userAgent.contains("APP_WEBVIEW")) {
            isMobileApp = true
        }
        request.cookies?.let {
            it.map {
                if (it.name == "ANDROID_PUSH_TOKEN") {
                    _pushToken = it.value.replace("%3A", ":")
                    _device = "ANDROID"
                } else if (it.name == "IOS_PUSH_TOKEN") {
                    _pushToken = it.value
                    _device = "IOS"
                }
            }
        }

        var user:User? = null
        when (socialTypeEnum) {
            SocialTypeEnum.GOOGLE -> {
                user = User(
                    id = null,
                    uid = userAttributes.get("sub") as String,
                    socialType = socialTypeEnum,
                    profile = userAttributes.get("name") as String,
                    email = userAttributes.get("email") as String,
                    profileImgUrl = (userAttributes.get("picture") as String).replace("http://", "https://"),
                    accessToken = oAuth2UserRequest.accessToken.tokenValue,
                    tokenExpiresAt = LocalDateTime.ofInstant(oAuth2UserRequest.accessToken.expiresAt, ZoneOffset.UTC)
                        .plusHours(9),
                    pushToken = _pushToken,
                    device = _device,
                    regDttm = LocalDateTime.now()
                )
            }
            SocialTypeEnum.FACEBOOK -> {
                user = User(
                    id = null,
                    uid = userAttributes.get("id").toString(),
                    socialType = socialTypeEnum,
                    profile = userAttributes.get("name").toString(),
                    email = userAttributes.get("email").toString(),
                    profileImgUrl = "https://kimsea.me/assets/img/defaulticon.png",
                    accessToken = oAuth2UserRequest.accessToken.tokenValue,
                    tokenExpiresAt = LocalDateTime.ofInstant(oAuth2UserRequest.accessToken.expiresAt, ZoneOffset.UTC)
                        .plusHours(9),
                    pushToken = _pushToken,
                    device = _device,
                    regDttm = LocalDateTime.now()
                )
            }
            SocialTypeEnum.KAKAO -> {
                val properties = userAttributes.get("properties") as Map<String, Any>
                var email = "empty"
                if (properties.get("email") != null) {
                    email = properties.get("email").toString()
                }
                user = User(
                    id = null,
                    uid = userAttributes.get("id").toString(),
                    socialType = socialTypeEnum,
                    profile = properties.get("nickname").toString(),
                    email = email,
                    profileImgUrl = properties.get("profile_image").toString().replace("http://", "https://"),
                    accessToken = oAuth2UserRequest.accessToken.tokenValue,
                    tokenExpiresAt = LocalDateTime.ofInstant(oAuth2UserRequest.accessToken.expiresAt, ZoneOffset.UTC)
                        .plusHours(9),
                    pushToken = _pushToken,
                    device = _device,
                    regDttm = LocalDateTime.now()
                )
            }
            SocialTypeEnum.NAVER -> {
                user = User(
                    id = null,
                    uid = userAttributes.get("id").toString(),
                    socialType = socialTypeEnum,
                    profile = userAttributes.get("nickname").toString(),
                    email = userAttributes.get("email").toString(),
                    profileImgUrl = userAttributes.get("profile_image").toString().replace("http://", "https://"),
                    accessToken = oAuth2UserRequest.accessToken.tokenValue,
                    tokenExpiresAt = LocalDateTime.ofInstant(oAuth2UserRequest.accessToken.expiresAt, ZoneOffset.UTC)
                        .plusHours(9),
                    pushToken = _pushToken,
                    device = _device,
                    regDttm = LocalDateTime.now()
                )
            }
            else -> {

            }
        }

        var existUser = userRepository.findByUidAndSocialType(user!!.uid, user.socialType)
        existUser?.apply {
            profile = user.profile
            email = user.email
            profileImgUrl = user.profileImgUrl
            accessToken = user.accessToken
            tokenExpiresAt = user.tokenExpiresAt
            regDttm = LocalDateTime.now()
        }

        if (existUser != null && isMobileApp) {
            existUser.pushToken = user.pushToken
            existUser.device = user.device
        }

        if (existUser == null) {
            user.apiKey = KeyUtil.genApiKey()
            existUser = userRepository.save(user)
        }

        if (existUser.apiKey == null) {
            existUser.apiKey = KeyUtil.genApiKey()
        }

        val exitWallet = walletRepository.findByIdOrNull(existUser.id!!)
        if (exitWallet == null) {
            with(ethereumService.create()) {
                Wallet(
                    userId = existUser.id!!,
                    address = address,
                    password = password,
                    walletDat = walletFile,
                    availableGem = 0,
                )
            }.run {
                walletRepository.save(this)
            }
        }
        return existUser
    }
}