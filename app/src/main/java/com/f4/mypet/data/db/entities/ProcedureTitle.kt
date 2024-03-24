package com.f4.mypet.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity
data class ProcedureTitle(
    val name: String,
    val type: Int,
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0
)

data class TitleWithProcedures(
    @Embedded val procedure: Procedure,
    @Relation(
        parentColumn = "id",
        entityColumn = "title"
    )
    val procedures: List<Procedure>
)
