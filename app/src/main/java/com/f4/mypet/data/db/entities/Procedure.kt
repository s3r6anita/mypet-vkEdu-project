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
data class Procedure(
    val title: Int, // название
    val isDone: Int, // выполнена ли: 0 - нет, 1 - да
    val frequency: Int, // частота выполнения
    @Nullable
    val dateDone: LocalDateTime?, // когда выполнена
    val dateCreated: LocalDateTime, // когда создана
    val notes: String, // заметки
    @Nullable
    val reminder: LocalDateTime?, // время уведомлений
    val pet: Int, // питомец
    val inHistory: Int, // нужно ли добавить в медкарту: 0 - нет, 1 - да
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)


