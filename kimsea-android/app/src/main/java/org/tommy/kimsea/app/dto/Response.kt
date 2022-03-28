package org.tommy.kimsea.app.dto

data class Response<T>(
    var code: String,
    var msg: String,
    var data: T?
)