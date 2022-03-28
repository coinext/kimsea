package io.tommy.kimsea.web.services

import io.tommy.kimsea.web.utils.HttpUtil
import okhttp3.FormBody
import okhttp3.Request
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class TelegramAlaramService(
    @Value("\${telegram.api.url}")
    val TELEGRAM_BOT_API:String
) {
    val okHttpClient = HttpUtil.getUnsafeOkHttpClient(30, 10, 10, 10)

    fun sendMessage(msg:String) : String {
        val req = Request.Builder()
            .url("${TELEGRAM_BOT_API}/sendMessage")
            .addHeader("Content-Type", "application/json")
            .post(
                FormBody.Builder()
                    .add("chat_id", "1009701773")
                    .add("text", msg)
                    .build())
            .build()

        val res = okHttpClient.newCall(req).execute()
        return res.body!!.string()
    }
}