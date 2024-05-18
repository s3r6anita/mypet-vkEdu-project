package com.f4.mypet.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity
data class Pet(
    val name: String,
    val kind: String, // кошка собака морж
    val breed: String, // порода
    val sex: String, // "Самка" | "Самец"
    val birthday: LocalDate, // дата рождения
    val color: String, // окрас
    val coat: String, // вид шерсти
    val microchipNumber: String, // 15 цифр
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)
