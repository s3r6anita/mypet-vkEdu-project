package com.f4.mypet.util

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date

val regex = "^[а-яА-Я-\\s-]+$".toRegex()
val dateRegex = "^\\d{2}\\.\\d{2}\\.\\d{4}$".toRegex()
val chipNumberRegex = "^\\d{15}$".toRegex()
val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()
val passwordRegex = "^(?=.*[0-9])(?=.*[a-zA-Z]).{8,}\$".toRegex()

fun validate(name: String): Boolean = name.matches(regex)

fun validateEmail(email: String) = email.matches(emailRegex)

fun validatePassword(password: String) = password.matches(passwordRegex)

fun validateMicrochipNumber(chipNumber: String): Boolean = chipNumber.matches(chipNumberRegex)

fun validateDate(dateString: String): Boolean = dateString.matches(dateRegex)

fun validateBirthday(date: LocalDate): Boolean {
//    throw IllegalArgumentException("Ошибка парсинга введенной даты")
    if (date > Date().toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
    ) {
        throw IllegalArgumentException("Дата больше текущей")
    }
    return validateDate(date.format(PetDateTimeFormatter.date))
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
        return year <= LocalDate.now().year
    }
}

@OptIn(ExperimentalMaterial3Api::class)
object PresentOrFutureSelectableDates: SelectableDates {
    override fun isSelectableDate(utcTimeMillis: Long): Boolean {
        return utcTimeMillis >= System.currentTimeMillis()
    }

    override fun isSelectableYear(year: Int): Boolean {
        return year >= LocalDate.now().year
    }
}
