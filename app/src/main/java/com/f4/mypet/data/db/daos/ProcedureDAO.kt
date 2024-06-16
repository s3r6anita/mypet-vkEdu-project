package com.f4.mypet.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.f4.mypet.data.db.entities.Procedure
import kotlinx.coroutines.flow.Flow

@Dao
interface ProcedureDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(procedure: Procedure)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(procedures: List<Procedure>)

    @Delete
    suspend fun delete(procedure: Procedure)

    @Update
    suspend fun update(procedure: Procedure)

    @Query("DELETE FROM procedure WHERE pet = :petId")
    suspend fun deleteProceduresForPet(petId: Int)

    @Query("SELECT * from procedure where pet = :petId order by dateDone asc")
    fun getProceduresForPet(petId: Int): Flow<List<Procedure>>

    @Query("SELECT * from procedure where id = :procedureId")
    fun getProcedure(procedureId: Int): Procedure

    @Query("DELETE from procedure")
    fun deleteAll()

}
