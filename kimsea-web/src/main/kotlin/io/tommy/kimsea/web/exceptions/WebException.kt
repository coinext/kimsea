package io.tommy.kimsea.web.exceptions

import io.tommy.kimsea.web.enums.CodeEnum

class WebException : RuntimeException {
    val codeEnum: CodeEnum

    constructor(codeEnum: CodeEnum) : super(codeEnum.message) {
        this.codeEnum = codeEnum
    }

    constructor(codeEnum: CodeEnum, cause: Throwable?) : super(codeEnum.message, cause) {
        this.codeEnum = codeEnum
    }

    constructor(codeEnum: CodeEnum, message: String?) : super(message) {
        this.codeEnum = codeEnum
    }

    constructor(codeEnum: CodeEnum, message: String?, cause: Throwable?) : super(message, cause) {
        this.codeEnum = codeEnum
    }
}