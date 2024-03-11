package com.f4.mypet.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.f4.mypet.db.entities.Pet
import kotlinx.coroutines.flow.Flow


@Dao
interface PetDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pet: Pet)

    @Query("SELECT * from pet")
    fun getPets(): Flow<List<Pet>>
}
