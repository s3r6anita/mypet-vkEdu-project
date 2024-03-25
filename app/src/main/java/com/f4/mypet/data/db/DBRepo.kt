package com.f4.mypet.data.db

import com.f4.mypet.data.db.daos.MedRecordDAO
import com.f4.mypet.data.db.daos.PetDAO
import com.f4.mypet.data.db.daos.PrTitleDAO
import com.f4.mypet.data.db.daos.ProcedureDAO
import com.f4.mypet.data.db.entities.Pet
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface Repository {
    suspend fun insertPet(pet: Pet)
    suspend fun updatePet(pet: Pet)
    suspend fun getPets(): Flow<List<Pet>>
    suspend fun getPet(petId: Int): Flow<Pet>
    suspend fun getPetForCU(petId: Int): Pet
}

class DBRepository @Inject constructor(
    private val petDAO: PetDAO,
    private val medRecordDAO: MedRecordDAO,
    private val procedureDAO: ProcedureDAO,
    private val prTitleDAO: PrTitleDAO
) : Repository {
    override suspend fun insertPet(pet: Pet) {
        petDAO.insert(pet)
    }

    override suspend fun updatePet(pet: Pet) {
        petDAO.update(pet)
    }

    override suspend fun getPets(): Flow<List<Pet>> {
        return petDAO.getPets()
    }

    override suspend fun getPet(petId: Int): Flow<Pet> {
        return petDAO.getPet(petId)
    }

    override suspend fun getPetForCU(petId: Int): Pet {
        return petDAO.getPetForCU(petId)
    }
}