package com.f4.mypet

import java.util.Date

val regex = "^[а-яА-Я-\\s-]+$".toRegex()
val timeRegex = "^\\d{2}\\.\\d{2}\\.\\d{4}$".toRegex()
val chipNumberRegex = "^\\d{15}$".toRegex()

fun validate(name: String): Boolean = name.matches(regex)

fun validateMicrochipNumber(chipNumber: String): Boolean = chipNumber.matches(chipNumberRegex)

fun validateDate(dateString: String): Boolean = dateString.matches(timeRegex)

fun validateBirthday(dateString: String): Boolean {
    if (dateFormat.parse(dateString)!! > Date()) {
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
