package com.f4.mypet.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.f4.mypet.data.db.entities.Frequency

@Dao
interface FrequencyDAO {
    @Insert(onConflict = OnConflictStrategy.NONE)
    suspend fun insert(frequency: Frequency): Long

    @Update
    suspend fun update(frequency: Frequency)

    @Query("SELECT * from frequency where id = :frequencyId")
    fun getFrequency(frequencyId: Int): Frequency

    @Query("SELECT distinct * from frequency order by id asc")
    fun getOptions(): List<Frequency>
}
