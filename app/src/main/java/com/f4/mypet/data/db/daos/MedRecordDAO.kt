package com.f4.mypet.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.f4.mypet.data.db.entities.MedRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface MedRecordDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(medRecord: MedRecord)

    @Update
    suspend fun update(medRecord: MedRecord)

    @Delete
    suspend fun delete(medRecord: MedRecord)

    @Query("SELECT * from MedRecord where pet = :petId")
    fun getMedRecords(petId: Int): Flow<List<MedRecord>>

    @Query("SELECT * from MedRecord where id = :medRecordId")
    fun getMedRecord(medRecordId: Int): MedRecord

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(medRecords: List<MedRecord>)

    @Query("SELECT * from MedRecord where pet = :petId order by date asc")
    fun getMedRecordsForPet(petId: Int): Flow<List<MedRecord>>

    @Query("DELETE FROM medrecord WHERE pet = :petId")
    suspend fun deleteMedRecordsForPet(petId: Int)

    @Query("DELETE from medrecord")
    fun deleteAll()
}
