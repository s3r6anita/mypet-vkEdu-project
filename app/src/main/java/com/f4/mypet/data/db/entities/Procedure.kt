package com.f4.mypet.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import javax.annotation.Nullable

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Pet::class,
            parentColumns = ["id"],
            childColumns = ["pet"]
        ),
        ForeignKey(
            entity = ProcedureTitle::class,
            parentColumns = ["id"],
            childColumns = ["title"]
        )]
)
// TODO @Stable or correct to get StabilityInferred
data class Procedure(
    val title: Int, // название
    val isDone: Int, // выполнена ли: 0 - нет, 1 - да
    val frequency: Int, // раз в сколько часов повторять
    val dateDone: LocalDateTime, // когда следует выполнить
    val notes: String, // заметки
    @Nullable
    val reminder: LocalDateTime?, // дата и время уведомления
    val pet: Int, // питомец
    val inMedCard: Int, // нужно ли добавить в медкарту: 0 - нет, 1 - да
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)
