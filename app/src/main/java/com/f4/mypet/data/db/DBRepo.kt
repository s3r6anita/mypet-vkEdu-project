package com.f4.mypet.data.db

import com.f4.mypet.data.db.daos.MedRecordDAO
import com.f4.mypet.data.db.daos.PetDAO
import com.f4.mypet.data.db.daos.PrTitleDAO
import com.f4.mypet.data.db.daos.ProcedureDAO
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface Repository {
    suspend fun insertPet(pet: Pet)
    suspend fun updatePet(pet: Pet)
    suspend fun getPets(): Flow<List<Pet>>
    suspend fun getPet(petId: Int): Flow<Pet>
    suspend fun getPetForCU(petId: Int): Pet
    suspend fun getProceduresForPet(petId: Int): Flow<List<Procedure>>
    suspend fun getProcedureTitles(): List<ProcedureTitle>
    suspend fun getProcedure(procedureId: Int): Flow<Procedure>
    suspend fun getProcedureTypes(): List<ProcedureType>
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

    override suspend fun getProceduresForPet(petId: Int): Flow<List<Procedure>> {
        return procedureDAO.getProceduresForPet(petId)
    }

    override suspend fun getProcedureTitles(): List<ProcedureTitle> {
        return prTitleDAO.getProcedureTitles()
    }

    override suspend fun getProcedure(procedureId: Int): Flow<Procedure> {
        return procedureDAO.getProcedure(procedureId)
    }

    override suspend fun getProcedureTypes(): List<ProcedureType> {
        return prTitleDAO.getProcedureTypes()
    }
}
