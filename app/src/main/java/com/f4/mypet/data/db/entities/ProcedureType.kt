package com.f4.mypet.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class ProcedureType(
    val name: String,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)

data class TypeWithTitles(
    @Embedded val type: ProcedureType,
    @Relation(
        parentColumn = "id",
        entityColumn = "type"
    )
    val procedures: List<ProcedureTitle>
)
