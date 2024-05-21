package com.f4.mypet.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Frequency(
    var option: String, // единицы измерения
    var frequency: String, // частота
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)
