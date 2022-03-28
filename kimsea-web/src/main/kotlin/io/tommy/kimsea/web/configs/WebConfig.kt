package io.tommy.kimsea.web.configs

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.tommy.kimsea.web.utils.HttpUtil
import io.tommy.kimsea.web.utils.SimpleMinifierTemplateHandlerEx
import io.tommy.kimsea.web.utils.converters.GsonDateConverter
import io.tommy.kimsea.web.utils.converters.GsonLocalDateTimeAdapter
import okhttp3.OkHttpClient
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.FormHttpMessageConverter
import org.springframework.http.converter.HttpMessageConverter
import org.springframework.http.converter.StringHttpMessageConverter
import org.springframework.http.converter.json.GsonHttpMessageConverter
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import org.thymeleaf.extras.minify.dialect.MinifierDialect
import org.thymeleaf.spring5.SpringTemplateEngine
import java.nio.charset.Charset
import java.time.LocalDateTime
import java.util.*


@Configuration
class WebConfig(
    val springTemplateEngine:SpringTemplateEngine
) : WebMvcConfigurer {

    init {
       springTemplateEngine.addDialect(MinifierDialect(SimpleMinifierTemplateHandlerEx::class.java))
    }

   override fun configureMessageConverters(converters: MutableList<HttpMessageConverter<*>?>) {
        val msgConverter = GsonHttpMessageConverter()
        msgConverter.gson = gson()
        converters.add(msgConverter)
        converters.add(StringHttpMessageConverter(Charset.forName("UTF-8")))
        converters.add(FormHttpMessageConverter())
    }

    @Bean
    fun gson(): Gson {
        return GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
            .setDateFormat("yyyy'-'MM'-'dd'T'HH':'mm':'ss'.'SSS'Z'")
            .registerTypeAdapter(Date::class.java, GsonDateConverter())
            .registerTypeAdapter(LocalDateTime::class.java, GsonLocalDateTimeAdapter())
            .create()
    }

    @Bean
    fun okHttpClient(): OkHttpClient {
        return HttpUtil.getUnsafeOkHttpClient(
            100,
            10L,
            10L,
            10L
        )
    }
}