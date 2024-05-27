package com.f4.mypet.ui.screens.profile.show.screencomponents

import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.util.PetDateTimeFormatter

fun formatPet(pet: Pet): String {
    return buildString {
        append("Кличка: ${pet.name}\n")
        append("Вид: ${pet.kind}\n")
        append("Порода: ${pet.breed}\n")
        append("Пол: ${pet.sex}\n")
        append("Дата рождения: ${pet.birthday.format(PetDateTimeFormatter.date)}\n")
        append("Тип шерсти: ${pet.coat}\n")
        append("Окрас: ${pet.color}\n")
        append("Номер микрочипа: ${pet.microchipNumber}\n")
    }
}
