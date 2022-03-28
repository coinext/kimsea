package io.tommy.kimsea.web.utils.converters

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAccessor
import javax.persistence.Converter

@Converter(autoApply = true)
class LocalDateTimeConverter : JsonSerializer<LocalDateTime?>,
    JsonDeserializer<LocalDateTime> {
    override fun serialize(src: LocalDateTime?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        return JsonPrimitive(FORMATTER.format(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDateTime {
        return FORMATTER.parse(
            json.asString
        ) { temporal: TemporalAccessor? -> LocalDateTime.from(temporal) }
    }

    companion object {
        /** Formatter.  */
        private val FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    }
}