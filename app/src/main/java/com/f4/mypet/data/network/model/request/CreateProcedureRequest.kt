package com.f4.mypet.data.network.model.request

import java.time.LocalDateTime

data class CreateProcedureRequest( // without "id"
    val title: Int,
    val isDone: Int,
    val frequency: String,
    val frequencyOption: Int,
    val dateDone: LocalDateTime,
    val notes: String,
    val reminder: LocalDateTime?,
    val pet: Int,
    val inMedCard: Int
)