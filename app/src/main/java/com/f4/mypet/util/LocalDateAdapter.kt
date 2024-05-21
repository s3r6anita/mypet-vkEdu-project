package com.f4.mypet.util

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.time.LocalDate
import java.time.LocalDateTime

class LocalDateAdapter : TypeAdapter<LocalDate?>() {
    override fun write(jsonWriter: JsonWriter?, value: LocalDate?) {
        if (value == null) {
            jsonWriter?.nullValue()
        } else {
            jsonWriter?.value(value.format(PetDateTimeFormatter.date))
        }
    }

    override fun read(jsonReader: JsonReader): LocalDate? {
        return if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull()
            null
        } else {
            LocalDate.parse(jsonReader.nextString(), PetDateTimeFormatter.date)
        }
    }
}

class LocalDateTimeAdapter : TypeAdapter<LocalDateTime?>() {

    override fun write(jsonWriter: JsonWriter?, value: LocalDateTime?) {
        if (value == null) {
            jsonWriter?.nullValue()
        } else {
            jsonWriter?.value(value.format(PetDateTimeFormatter.dateTime))
        }
    }

    override fun read(jsonReader: JsonReader): LocalDateTime? {
        return if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull()
            null
        } else {
            LocalDateTime.parse(jsonReader.nextString(), PetDateTimeFormatter.dateTime)
        }
    }
}
