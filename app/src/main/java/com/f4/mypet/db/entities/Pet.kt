package com.f4.mypet.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity
data class Pet(
    val name: String,
    val kind: String, // кошка собака морж
    val breed: String, // порода
    val sex: Boolean, // "Мужской" | "Женский"
    val birthday: Date, // TODO: нужен конвертер типов для даты
    val color: String, // окрас
    val coat: String, // вид шерсти
    val microchipNumber: String, // 15 цифр
    // добавим когда появятся остальные таблицы
//    var procedures: MutableList<Procedure>,
//    var therapies: MutableList<Therapy>,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "pet_id") val id: Int = 0
)
