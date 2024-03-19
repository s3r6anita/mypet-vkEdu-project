package com.f4.mypet.db.repositories

import com.f4.mypet.db.daos.MedRecordDAO
import com.f4.mypet.db.daos.PetDAO
import com.f4.mypet.db.daos.PrTitleDAO
import com.f4.mypet.db.daos.ProcedureDAO
import com.f4.mypet.db.entities.Pet
import kotlinx.coroutines.flow.Flow

interface DBRepositoryInterface {
    fun getPets(): Flow<List<Pet>>

    suspend fun insertPet(pet: Pet)

}

class DBRepository(
    private val petDAO: PetDAO,
    private val medRecordDAO: MedRecordDAO,
    private val procedureDAO: ProcedureDAO,
    private val prTitleDAO: PrTitleDAO
) : DBRepositoryInterface {
    override fun getPets(): Flow<List<Pet>> {
        return petDAO.getPets()
    }

    override suspend fun insertPet(pet: Pet) {
        petDAO.insert(pet)
    }
}