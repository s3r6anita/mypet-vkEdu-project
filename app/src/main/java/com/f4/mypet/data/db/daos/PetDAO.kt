package com.f4.mypet.data.db.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.f4.mypet.data.db.entities.Pet
import kotlinx.coroutines.flow.Flow


@Dao
interface PetDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pet: Pet)

    @Query("SELECT * from pet")
    fun getPets(): Flow<List<Pet>>

    @Query("SELECT * from pet where id = :petId")
    fun getPet(petId: Int): Flow<Pet>

    @Query("SELECT * from pet where id = :petId")
    fun getPetForCU(petId: Int): Pet

}
