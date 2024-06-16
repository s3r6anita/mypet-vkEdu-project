package com.f4.mypet.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity
data class MedRecord(
    val title: String, // название
    val date: LocalDateTime, // дата
    val notes: String, // заметки
    val pet: Int, // питомец
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)
