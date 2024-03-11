package com.f4.mypet.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.f4.mypet.db.entities.Procedure
import kotlinx.coroutines.flow.Flow

@Dao
interface ProcedureDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(procedure: Procedure)

    @Query("SELECT * from procedure where pet = :petId")
    fun getProceduresForPet(petId: Int): Flow<List<Procedure>>
}
