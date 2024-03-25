package com.f4.mypet

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Date

val regex = "^[а-яА-Я-\\s-]+$".toRegex()
val dateRegex = "^\\d{2}\\.\\d{2}\\.\\d{4}$".toRegex()
val chipNumberRegex = "^\\d{15}$".toRegex()

fun validate(name: String): Boolean = name.matches(regex)

fun validateMicrochipNumber(chipNumber: String): Boolean = chipNumber.matches(chipNumberRegex)

fun validateDate(dateString: String): Boolean = dateString.matches(dateRegex)

fun validateBirthday(dateString: String): Boolean {
    if ((LocalDateTime.parse(dateString, PetDateTimeFormatter.date)
            ?: throw IllegalArgumentException("Ошибка парсинга введенной даты")) > Date().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
    ) {
        throw IllegalArgumentException("Дата больше сегодняшней")
    }
    return validateDate(dateString)
}

fun validateTime(timeString: String) {
    val regex = "^([0-1][0-9]|[2][0-3]):([0-5][0-9])$".toRegex()
    if ( !(timeString.matches(regex)) ) {
        throw IllegalArgumentException("Неверно указано время")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
object PastOrPresentSelectableDates: SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis <= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        Log.d("mytag", "${LocalDate.now().year}")
        return year <= LocalDate.now().year
    }
}
