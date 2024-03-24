package com.f4.mypet.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
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
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)

data class PetWithProcedures(
    @Embedded val pet: Pet,
    @Relation(
        parentColumn = "id",
        entityColumn = "pet"
    )
    val procedures: List<Procedure>
)

data class PetWithMedRecords(
    @Embedded val pet: Pet,
    @Relation(
        parentColumn = "id",
        entityColumn = "pet"
    )
    val medRecord: List<MedRecord>
)
