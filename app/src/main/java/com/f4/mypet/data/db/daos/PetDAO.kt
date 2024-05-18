package com.f4.mypet.data.db.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.f4.mypet.data.db.entities.Pet


@Dao
interface PetDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(pet: Pet)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(pets: List<Pet>)

    @Update
    suspend fun update(pet: Pet)

    @Delete
    suspend fun delete(pet: Pet)

    @Query("SELECT * from pet")
    fun getPets(): List<Pet>

    @Query("SELECT * from pet where id = :petId")
    fun getPet(petId: Int): Pet

    @Query("SELECT * from pet where id = :petId")
    fun getPetForCU(petId: Int): Pet

    @Query("DELETE from pet")
    fun deleteAll()

}
