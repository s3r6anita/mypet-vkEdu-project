package com.f4.mypet.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date


@Entity
data class MedRecord(
    val title: Int, // название
    val date: Date, // дата
    val notes: String, // заметки
    val pet: Int, // питомец
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)
