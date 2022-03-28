package io.tommy.kimsea.web.annotations

import org.springframework.transaction.annotation.Transactional

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CLASS
)
@Retention(value = AnnotationRetention.RUNTIME)
@Transactional(rollbackFor = [Throwable::class])
annotation class SoftTransactional