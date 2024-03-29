package com.f4.mypet.data.db

import androidx.room.TypeConverter
import com.f4.mypet.PetDateTimeFormatter

import java.time.LocalDateTime



class Converters {
    @TypeConverter
    fun stringToDate(dateStr: String?): LocalDateTime? {
        return dateStr.let {
            LocalDateTime.parse(
                dateStr ?: "01.01.1001 00:00",
                PetDateTimeFormatter.dateTime
            )
        }
    }

    @TypeConverter
    fun dateToString(date: LocalDateTime?): String? {
        return if (date?.let { date.format(PetDateTimeFormatter.dateTime) } == "01.01.1001 00:00") {
            "не установлено"
        } else {
            date?.let { date.format(PetDateTimeFormatter.dateTime) }
        }
    }
}
