package com.f4.mypet.db.daos

import android.icu.text.CaseMap.Title
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.f4.mypet.db.entities.ProcedureTitle
import kotlinx.coroutines.flow.Flow

@Dao
interface PrTitleDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(title: ProcedureTitle)

    @Query("SELECT * from procedureTitle where type = :titleType")
    fun getTitlesWithType(titleType: Int): Flow<List<Title>>
}
