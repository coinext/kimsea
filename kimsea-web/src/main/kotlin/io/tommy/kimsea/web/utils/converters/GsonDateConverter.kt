package io.tommy.kimsea.web.utils.converters

import com.google.gson.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*

class GsonDateConverter : JsonSerializer<Date?>,
    JsonDeserializer<Date?> {
    override fun serialize(src: Date?, typeOfSrc: Type, context: JsonSerializationContext): JsonElement {
        val simpleDateFormat = SimpleDateFormat(FORMAT)
        return JsonPrimitive(simpleDateFormat.format(src))
    }

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Date? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ")
        return simpleDateFormat.parse(json.asString)
    }

    companion object {
        private const val FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
    }
}