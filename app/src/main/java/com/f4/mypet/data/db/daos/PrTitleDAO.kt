package com.f4.mypet.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType

@Dao
interface PrTitleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(title: ProcedureTitle)

    @Query("SELECT * from procedureTitle")
    fun getProcedureTitles(): List<ProcedureTitle>

    @Query("SELECT * from procedureType")
    fun getProcedureTypes(): List<ProcedureType>

    @Query("SELECT * from procedureTitle where type = :titleType")
    fun getTitlesWithType(titleType: Int): List<ProcedureTitle>
}
