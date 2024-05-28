package com.f4.mypet.data.db.entities

import androidx.compose.runtime.Stable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime
import javax.annotation.Nullable

@Entity
@Stable
data class Procedure(
    val title: Int, // название
    val isDone: Int, // выполнена ли: 0 - нет, 1 - да
    val frequency: String, // период частоты
    val frequencyOption: Int, // ссылка на частоту
    val dateDone: LocalDateTime, // когда следует выполнить
    val notes: String, // заметки
    @Nullable
    val reminder: LocalDateTime?, // дата и время уведомления
    val pet: Int, // питомец
    val inMedCard: Int, // нужно ли добавить в медкарту: 0 - нет, 1 - да
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)
