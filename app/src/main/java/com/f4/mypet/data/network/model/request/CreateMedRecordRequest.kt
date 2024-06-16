package com.f4.mypet.data.network.model.request

import java.time.LocalDateTime

class CreateMedRecordRequest(
    val title: String, // название
    val date: LocalDateTime, // дата
    val notes: String, // заметки
    val pet: Int, // питомец
)
