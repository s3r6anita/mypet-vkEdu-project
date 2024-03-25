package com.f4.mypet.data.db

import androidx.room.TypeConverter
import com.f4.mypet.PetDateTimeFormatter

import java.time.LocalDateTime



class Converters {
    @TypeConverter
    fun stringToDate(dateStr: String?): LocalDateTime? {
        return dateStr.let { LocalDateTime.parse(dateStr, PetDateTimeFormatter.dateTime) }
    }

    @TypeConverter
    fun dateToString(date: LocalDateTime?): String? {
        return date?.let { date.format(PetDateTimeFormatter.dateTime) };
    }
}
