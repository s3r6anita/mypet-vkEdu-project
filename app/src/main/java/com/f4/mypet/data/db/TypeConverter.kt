package com.f4.mypet.data.db

import androidx.room.TypeConverter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class Converters {
    private val formatter = DateTimeFormatter.ofPattern("dd'.'MM'.'yyyy HH':'mm")

    @TypeConverter
    fun StringToDate(dateStr: String?): LocalDateTime? {
        return dateStr.let { LocalDateTime.parse(dateStr, formatter) }
    }

    @TypeConverter
    fun DateToString(date: LocalDateTime?): String? {
        return date?.let { date.format(formatter) };
    }
}
