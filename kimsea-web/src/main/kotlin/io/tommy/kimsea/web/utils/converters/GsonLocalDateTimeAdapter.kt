package io.tommy.kimsea.web.utils.converters

import com.google.gson.*
import java.lang.reflect.Type
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class GsonLocalDateTimeAdapter : JsonSerializer<LocalDateTime?>,
    JsonDeserializer<LocalDateTime> {
    override fun serialize(
        localDateTime: LocalDateTime?,
        srcType: Type,
        context: JsonSerializationContext
    ): JsonElement {
        return JsonPrimitive(DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(localDateTime))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): LocalDateTime {
        try {
            return LocalDateTime.parse(json.asString, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        } catch (ex:Exception) {
            return LocalDateTime.parse(json.asString.trim().replace(" ", "T"), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        }
    }
}