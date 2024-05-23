package com.f4.mypet.data.network.model.request

import java.time.LocalDate

data class UpdatePetRequest(
    val id: Int,
    val name: String,
    val kind: String,
    val breed: String,
    val sex: String,
    val birthday: LocalDate,
    val color: String,
    val coat: String,
    val microchipNumber: String
)
