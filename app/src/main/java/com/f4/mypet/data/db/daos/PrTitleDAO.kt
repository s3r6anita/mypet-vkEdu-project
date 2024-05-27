package com.f4.mypet.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType
import kotlinx.coroutines.flow.Flow

@Dao
interface PrTitleDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(title: ProcedureTitle): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(titles: List<ProcedureTitle>)

    @Update
    suspend fun update(title: ProcedureTitle)

    @Query("SELECT * from procedureTitle")
    fun getProcedureTitles(): Flow<List<ProcedureTitle>>

    @Query("SELECT * from procedureTitle")
    fun getProcedureTitlesForCU(): List<ProcedureTitle>

    @Query("SELECT * from procedureType")
    fun getProcedureTypes(): List<ProcedureType>

    @Query("SELECT * from procedureTitle where type = :titleType")
    fun getTitlesWithType(titleType: Int): List<ProcedureTitle>

    @Query("DELETE from procedureTitle")
    fun deleteAll()
}
