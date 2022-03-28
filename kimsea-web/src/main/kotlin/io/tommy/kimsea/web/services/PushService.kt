package io.tommy.kimsea.web.services

import com.google.auth.oauth2.GoogleCredentials
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.messaging.*
import io.tommy.kimsea.web.entity.domain.PushToken
import io.tommy.kimsea.web.entity.repository.PushTokenRepository
import io.tommy.kimsea.web.entity.repository.UserRepository
import io.tommy.kimsea.web.utils.logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.time.LocalDateTime
import java.util.concurrent.ConcurrentHashMap
import javax.annotation.PostConstruct

@Service
class PushService(
    val pushTokenRepository: PushTokenRepository,
    @Value("\${firebase.credentials.path}") val FIREBASE_CREDENTIALS_FILE_PATH:String,
    val userRepository: UserRepository
) {

    companion object {
        val TITLE = "KIMSEA"
    }
    val log = logger()
    val cachePushTokenMap = ConcurrentHashMap<String, Long>(100000)
    lateinit var firebaseMessaging: FirebaseMessaging

    @PostConstruct
    fun init() {
        val serviceAccount = FileInputStream(FIREBASE_CREDENTIALS_FILE_PATH)
        val options = FirebaseOptions.builder()
            .setCredentials(GoogleCredentials.fromStream(serviceAccount))
            .build()
        try {
            firebaseMessaging = FirebaseMessaging.getInstance(FirebaseApp.initializeApp(options))
        } catch (ex:Exception) {

        }
    }

    fun regToken(androidToken:String, iosToken:String) {
        if (androidToken.isBlank() && iosToken.isBlank()) {
            return
        }

        if (androidToken.isNotBlank() && iosToken.isBlank()) {
            val pushToken = PushToken(androidToken, "ANDROID", LocalDateTime.now())
            val sw = cachePushTokenMap.get(pushToken.token)
            if (sw == null) {
                pushTokenRepository.save(pushToken)
                cachePushTokenMap.put(pushToken.token, 1)
            }
        } else if (androidToken.isBlank() && iosToken.isNotBlank()) {
            val pushToken = PushToken(iosToken, "IOS", LocalDateTime.now())

            val sw = cachePushTokenMap.get(pushToken.token)
            if (sw == null) {
                pushTokenRepository.save(pushToken)
                cachePushTokenMap.put(pushToken.token, 1)
            }
        }

    }

    fun sendWithImg(title:String, imgUrl:String, msg:String, pushToken:String, device:String = "ANDROID") : String {
        log.info("PUSH SEND [sendWithImg]..$pushToken")

        lateinit var message: Message
        val m = msg//.substring(1, msg.length - 1)
        if (device == "IOS") {
            val alert = ApsAlert.builder().setTitle(title).setBody(m).build()
            val aps = Aps.builder().setAlert(alert).setContentAvailable(true).setMutableContent(false).build()
            val apnsConfig = ApnsConfig.builder().setAps(aps).build()

            message = Message.builder()
                .setToken(pushToken)
                .setApnsConfig(apnsConfig)
                .build()
        } else {
            message = Message.builder()
                .putData("title", title)
                .putData("message", m)
                .putData("img", imgUrl)
                .setToken(pushToken)
                .build()
        }

        return firebaseMessaging.send(message)
    }

    fun send(title:String, msg:String, pushToken:String, device:String) : String {
        lateinit var message: Message
        val m = msg//.substring(1, msg.length - 1)
        if (device == "IOS") {
            val alert = ApsAlert.builder().setTitle(title).setBody(m).build()
            val aps = Aps.builder().setAlert(alert).setContentAvailable(true).setMutableContent(false).build()
            val apnsConfig = ApnsConfig.builder().setAps(aps).build()

            message = Message.builder()
                .setToken(pushToken)
                .setApnsConfig(apnsConfig)
                .build()
        } else {
            message = Message.builder()
                .putData("title", title)
                .putData("message", m)
                .setToken(pushToken)
                .build()
        }

        return firebaseMessaging.send(message)
    }

    fun send(title:String = TITLE, userId:Long, msg:String, device:String = "ANDROID") : String {
        log.info("PUSH SEND..$userId")
        try {
            val user = userRepository.findOneByIdAndDeviceAndPushTokenIsNotNull(userId, device)
            if (user != null && user.pushToken != null && user.pushToken!!.isNotBlank()) {
                return send(title, msg, user.pushToken!!, device)
            }
        } catch (ex:Exception) {
            log.error(ex.message)
        }
        return ""
    }

    fun sendAllByCustomers(title:String = TITLE, msg:String) : Int {
        var cnt = 0
        for (device in listOf("ANDROID", "IOS")) {
            for (i in 0..10000) {
                val messages =
                    userRepository.findAllByDeviceAndPushTokenIsNotNull(device, PageRequest.of(i, 100)).flatMap { user ->
                        lateinit var message: Message
                        val m = msg.substring(1, msg.length - 1)
                        if (device == "IOS") {
                            val alert = ApsAlert.builder().setTitle(title).setBody(m).build()
                            val aps = Aps.builder().setAlert(alert).setContentAvailable(true).setMutableContent(false).build()
                            val apnsConfig = ApnsConfig.builder().setAps(aps).build()

                            message = Message.builder()
                                .setToken(user.pushToken)
                                .setApnsConfig(apnsConfig)
                                .build()
                        } else {
                            message = Message.builder()
                                .putData("title", title)
                                .putData("message", m)
                                .setToken(user.pushToken)
                                .build()
                        }
                        listOf(message)
                    }
                cnt += messages.size
                if (messages.size <= 0) {
                    break
                }

                try {
                    var res = firebaseMessaging.sendAll(messages)

                    log.info("res == ${res.responses.get(0).isSuccessful}")
                } catch (ex:Exception) {
                    log.error(ex.message)
                }
            }
        }
        return cnt
    }

    fun sendAllByUsers(title:String = TITLE, msg:String) : Int {
        var cnt = 0
        for (device in listOf("ANDROID", "IOS")) {
            for (i in 0..10000) {
                val messages = pushTokenRepository.findAllByDeviceAndTokenIsNotNull(device, PageRequest.of(i, 100))
                    .flatMap { pushToken ->
                        lateinit var message: Message
                        val m = msg.substring(1, msg.length - 1)
                        if (device == "IOS") {
                            val alert = ApsAlert.builder().setTitle(title).setBody(m).build()
                            val aps = Aps.builder().setAlert(alert).setContentAvailable(true).setMutableContent(false).build()
                            val apnsConfig = ApnsConfig.builder().setAps(aps).build()

                            message = Message.builder()
                                .setToken(pushToken.token)
                                .setApnsConfig(apnsConfig)
                                .build()
                        } else {
                            message = Message.builder()
                                .putData("title", title)
                                .putData("message", m)
                                .setToken(pushToken.token)
                                .build()
                        }
                        listOf(message)
                    }
                cnt += messages.size
                if (messages.size <= 0) {
                    break
                }
                try {
                    firebaseMessaging.sendAll(messages)
                } catch (ex:Exception) {
                    log.error(ex.message)
                }
            }
        }
        return cnt
    }
}