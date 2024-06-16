package com.f4.mypet.data.db

import androidx.room.TypeConverter
import com.f4.mypet.util.PetDateTimeFormatter
import java.time.LocalDate

import java.time.LocalDateTime

class Converters {
    @TypeConverter
    fun stringToDate(dateStr: String): LocalDate {
        return dateStr.let {
            LocalDate.parse(dateStr, PetDateTimeFormatter.date)
        }
    }

    @TypeConverter
    fun dateToString(date: LocalDate): String {
        return date.format(PetDateTimeFormatter.date)
    }

    @TypeConverter
    fun stringToDateTime(dateStr: String?): LocalDateTime? {
        return dateStr.let {
            var date = dateStr
            if (dateStr == "не установлено") {
                date = "01.01.1001 00:00"
            }
            LocalDateTime.parse(
                date ?: "01.01.1001 00:00",
                PetDateTimeFormatter.dateTime
            )
        }
    }

    @TypeConverter
    fun dateTimeToString(date: LocalDateTime?): String? {
        return if (date?.let { date.format(PetDateTimeFormatter.dateTime) } == "01.01.1001 00:00") {
            "не установлено"
        } else {
            date?.let { date.format(PetDateTimeFormatter.dateTime) }
        }
    }
}
