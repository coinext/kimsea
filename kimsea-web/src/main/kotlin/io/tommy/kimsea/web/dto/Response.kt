package io.tommy.kimsea.web.dto

import com.fasterxml.jackson.annotation.JsonIgnore
import io.tommy.kimsea.web.enums.CodeEnum

data class Response<T>(
    @JsonIgnore
    var codeEnum: CodeEnum? = CodeEnum.SUCCESS,
    var code: String = CodeEnum.SUCCESS.getCode(),
    var msg: String = "SUCCESS",
    var data: T?
) {

    constructor(codeEnum: CodeEnum, msg:String) : this(codeEnum, codeEnum.getCode(), msg, null)
    constructor(codeEnum: CodeEnum) : this(codeEnum, codeEnum.getCode(), codeEnum.message, null)
    constructor(codeEnum: CodeEnum, data: T) : this(codeEnum, codeEnum.getCode(), codeEnum.message, data)
    constructor(data: T): this(CodeEnum.SUCCESS, CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.message, data)
}