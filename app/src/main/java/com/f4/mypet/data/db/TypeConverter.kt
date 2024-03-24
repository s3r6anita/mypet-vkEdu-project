package com.f4.mypet.data.db

import androidx.room.TypeConverter
import java.text.DateFormat
import java.util.Date
import java.util.Locale


class Converters {
    @TypeConverter
    fun StringToDate(dateStr: String?): Date? {
        return dateStr.let { DateFormat.getDateInstance(DateFormat.LONG, Locale.GERMANY).parse(it) }
    }

    @TypeConverter
    fun DateToString(date: Date?): String? {
        return date?.let { DateFormat.getDateInstance(DateFormat.LONG, Locale.ENGLISH).format(it) };
    }
}
