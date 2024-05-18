package com.f4.mypet.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class LocalDateAdapter : TypeAdapter<LocalDate?>() {
    private val dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")

    override fun write(jsonWriter: JsonWriter?, value: LocalDate?) {
        if (value == null) {
            jsonWriter?.nullValue()
        } else {
            jsonWriter?.value(value.format(dateTimeFormatter))
        }
    }

    override fun read(jsonReader: JsonReader): LocalDate? {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull()
            return null
        } else {
            return LocalDate.parse(jsonReader.nextString(), dateTimeFormatter)
        }
    }
}
