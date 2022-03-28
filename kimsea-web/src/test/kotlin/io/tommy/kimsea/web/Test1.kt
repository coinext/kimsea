package io.tommy.kimsea.web

import io.tommy.kimsea.web.utils.HttpUtil
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.Request
import okhttp3.RequestBody
import java.io.File


class Test1

fun main(args: Array<String>) {
    val httpClient = HttpUtil.getUnsafeOkHttpClient(
        1,
        10L,
        10L,
        10L
    )

    val builder =MultipartBody.Builder()
        .setType("multipart/form-data".toMediaTypeOrNull()!!)
        .addFormDataPart("file", "/Users/tommy/Desktop/cover-6.jpg", RequestBody.create("image/png".toMediaTypeOrNull(),  File("/Users/tommy/Desktop/cover-6.jpg")))
        .build()

    val request: Request = Request.Builder()
        .header("Content-Type", "multipart/form-data")
        .header("Expecte", "")
        .url("https://ipfs.infura.io:5001/api/v0/add")
        .post(builder)
        .build()

    val res = httpClient.newCall(request).execute()
    println(res.body!!.string())
}

