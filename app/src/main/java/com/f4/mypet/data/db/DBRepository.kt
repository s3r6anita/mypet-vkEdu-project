package com.f4.mypet.data.db

import com.f4.mypet.data.db.daos.MedRecordDAO
import com.f4.mypet.data.db.daos.PetDAO
import com.f4.mypet.data.db.daos.PrTitleDAO
import com.f4.mypet.data.db.daos.ProcedureDAO
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface Repository {
    suspend fun insertPet(pet: Pet)
    suspend fun updatePet(pet: Pet)
    suspend fun removePet(pet: Pet)
    suspend fun removeProceduresForPet(petId: Int)
    suspend fun removeMedRecordsForPet(petId: Int)
    suspend fun getPets(): List<Pet>
    suspend fun getPet(petId: Int): Pet
    suspend fun getPetForCU(petId: Int): Pet
    suspend fun replaceAllData(pets: List<Pet>)
    suspend fun getProceduresForPet(petId: Int): Flow<List<Procedure>>
    suspend fun getProcedureTitles(): List<ProcedureTitle>
    suspend fun getProcedure(procedureId: Int): Flow<Procedure>
    suspend fun deleteProcedure(procedure: Procedure)
    suspend fun getProcedureTypes(): List<ProcedureType>

    suspend fun getMedRecordsForPet(petId: Int): Flow<List<MedRecord>>
    suspend fun getMedRecord(medRecord: Int): Flow<MedRecord>
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

    override suspend fun removePet(pet: Pet) {
        petDAO.delete(pet)
    }

    override suspend fun removeProceduresForPet(petId: Int) {
        procedureDAO.deleteProceduresForPet(petId)
    }

    override suspend fun removeMedRecordsForPet(petId: Int) {
        medRecordDAO.deleteMedRecordsForPet(petId)
    }

    override suspend fun getPets(): List<Pet> {
        return petDAO.getPets()
    }

    override suspend fun getPet(petId: Int): Pet {
        return petDAO.getPet(petId)
    }

    override suspend fun getPetForCU(petId: Int): Pet {
        return petDAO.getPetForCU(petId)
    }

    override suspend fun replaceAllData(pets: List<Pet>) {
        petDAO.deleteAll()
        procedureDAO.deleteAll()
        medRecordDAO.deleteAll()
        petDAO.insertAll(pets)
//        procedureDAO.insertAll(procedures)
//        medRecordDAO.insertAll(medRecords)
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

    override suspend fun deleteProcedure(procedure: Procedure) {
        procedureDAO.delete(procedure)
    }

    override suspend fun getProcedureTypes(): List<ProcedureType> {
        return prTitleDAO.getProcedureTypes()
    }


    override suspend fun getMedRecordsForPet(petId: Int): Flow<List<MedRecord>> {
        return medRecordDAO.getMedRecords(petId)
    }

    override suspend fun getMedRecord(medRecord: Int): Flow<MedRecord> {
        return medRecordDAO.getMedRecord(medRecord)
    }
}
