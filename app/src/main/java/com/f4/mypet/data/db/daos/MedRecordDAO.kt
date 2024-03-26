package com.f4.mypet.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.f4.mypet.data.db.entities.MedRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface MedRecordDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(medRecord: MedRecord)

    @Query("SELECT * from MedRecord where pet = :petId")
    fun getMedRecords(petId: Int): Flow<List<MedRecord>>
}
