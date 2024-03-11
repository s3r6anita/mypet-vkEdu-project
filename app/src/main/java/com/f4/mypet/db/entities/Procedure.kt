package com.f4.mypet.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

@Entity(foreignKeys = [
    ForeignKey(
        entity = Pet::class,
        parentColumns = ["id"],
        childColumns = ["pet"])])
data class Procedure(
    val title: Int, // название
    val isDone: Boolean, // выполнена ли
    val frequency: Int, // частота выполнения
    val dateDone: Date, // когда выполнена
    val dateCreated: Date, // когда создана
    val notes: String, // заметки
    val reminder: Date, // время уведомлений
    val pet: Int, // питомец
    val inHistory: Boolean, // нужно ли добавить в медкарту
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)
