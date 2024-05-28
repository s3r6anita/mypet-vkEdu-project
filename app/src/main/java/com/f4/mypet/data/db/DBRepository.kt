package com.f4.mypet.data.db

import com.f4.mypet.data.db.daos.FrequencyDAO
import com.f4.mypet.data.db.daos.MedRecordDAO
import com.f4.mypet.data.db.daos.PetDAO
import com.f4.mypet.data.db.daos.PrTitleDAO
import com.f4.mypet.data.db.daos.ProcedureDAO
import com.f4.mypet.data.db.entities.Frequency
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@SuppressWarnings("TooManyFunctions")
interface Repository {
    suspend fun replaceAllData(
        pets: List<Pet>, procedures: List<Procedure>, medRecords: List<MedRecord>,
        titles: List<ProcedureTitle>
    )
    suspend fun getPets(): List<Pet>
    suspend fun getPet(petId: Int): Pet
    suspend fun getPetForCU(petId: Int): Pet
    suspend fun insertPet(pet: Pet)
    suspend fun updatePet(pet: Pet)
    suspend fun removePet(pet: Pet)

    suspend fun getProceduresForPet(petId: Int): Flow<List<Procedure>>
    suspend fun insertListOfProcedures(procedures: List<Procedure>)
    suspend fun removeProceduresForPet(petId: Int)
    suspend fun getProcedureTypes(): List<ProcedureType>
    suspend fun getFrequencyOptions(): List<Frequency>
    suspend fun getProcedure(procedureId: Int): Procedure
    suspend fun insertProcedure(procedure: Procedure)
    suspend fun updateProcedure(procedure: Procedure)
    suspend fun deleteProcedure(procedure: Procedure)

    suspend fun getFrequency(frequencyId: Int): Frequency
    suspend fun insertFrequency(frequency: Frequency): Int
    suspend fun updateFrequency(frequency: Frequency)

    suspend fun getMedRecordsForPet(petId: Int): Flow<List<MedRecord>>
    suspend fun insertMedRecord(medRecord: MedRecord)
    suspend fun removeMedRecord(medRecord: MedRecord)
    suspend fun updateMedRecord(medRecord: MedRecord)
    suspend fun insertListOfMedRecords(medRecords: List<MedRecord>)
    suspend fun removeMedRecordsForPet(petId: Int)
    suspend fun getMedRecord(medRecord: Int): MedRecord
    suspend fun deleteMedRecord(medRecord: MedRecord)

    suspend fun replaceTitles(titles: List<ProcedureTitle>)
    suspend fun getProcedureTitlesForCU(): List<ProcedureTitle>
    suspend fun getProcedureTitles(): Flow<List<ProcedureTitle>>
    suspend fun insertTitle(title: ProcedureTitle): Int
    suspend fun updateTitle(title: ProcedureTitle)
}

@SuppressWarnings("TooManyFunctions")
class DBRepository @Inject constructor(
    private val petDAO: PetDAO,
    private val medRecordDAO: MedRecordDAO,
    private val procedureDAO: ProcedureDAO,
    private val prTitleDAO: PrTitleDAO,
    private val frequencyDAO: FrequencyDAO
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

    override suspend fun insertListOfProcedures(procedures: List<Procedure>) {
        procedureDAO.insertAll(procedures)
    }

    override suspend fun removeMedRecordsForPet(petId: Int) {
        medRecordDAO.deleteMedRecordsForPet(petId)
    }

    override suspend fun insertListOfMedRecords(medRecords: List<MedRecord>) {
        medRecordDAO.insertAll(medRecords)
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

    override suspend fun replaceAllData(
        pets: List<Pet>,
        procedures: List<Procedure>,
        medRecords: List<MedRecord>,
        titles: List<ProcedureTitle>
    ) {
        petDAO.deleteAll()
        procedureDAO.deleteAll()
        medRecordDAO.deleteAll()
        prTitleDAO.deleteAll()
        petDAO.insertAll(pets)
        procedureDAO.insertAll(procedures)
        medRecordDAO.insertAll(medRecords)
        prTitleDAO.insertAll(titles)
    }

    override suspend fun getProceduresForPet(petId: Int): Flow<List<Procedure>> {
        return procedureDAO.getProceduresForPet(petId)
    }

    override suspend fun getProcedureTitles(): Flow<List<ProcedureTitle>> {
        return prTitleDAO.getProcedureTitles()
    }

    override suspend fun getProcedureTitlesForCU(): List<ProcedureTitle> {
        return prTitleDAO.getProcedureTitlesForCU()
    }

    override suspend fun getProcedureTypes(): List<ProcedureType> {
        return prTitleDAO.getProcedureTypes()
    }

    override suspend fun getFrequencyOptions(): List<Frequency> {
        return frequencyDAO.getOptions()
    }

    override suspend fun getProcedure(procedureId: Int): Procedure {
        return procedureDAO.getProcedure(procedureId)
    }

    override suspend fun getFrequency(frequencyId: Int): Frequency {
        return frequencyDAO.getFrequency(frequencyId)
    }

    override suspend fun updateProcedure(procedure: Procedure) {
        procedureDAO.update(procedure)
    }

    override suspend fun updateTitle(title: ProcedureTitle) {
        prTitleDAO.update(title)
    }

    override suspend fun updateFrequency(frequency: Frequency) {
        frequencyDAO.update(frequency)
    }

    override suspend fun deleteProcedure(procedure: Procedure) {
        procedureDAO.delete(procedure)
    }

    override suspend fun insertProcedure(procedure: Procedure) {
        procedureDAO.insert(procedure)
    }

    override suspend fun insertTitle(title: ProcedureTitle): Int {
        return prTitleDAO.insert(title).toInt()
    }

    override suspend fun insertFrequency(frequency: Frequency): Int {
        return frequencyDAO.insert(frequency).toInt()
    }

    override suspend fun getMedRecordsForPet(petId: Int): Flow<List<MedRecord>> {
        return medRecordDAO.getMedRecordsForPet(petId)
    }

    override suspend fun getMedRecord(medRecord: Int): MedRecord {
        return medRecordDAO.getMedRecord(medRecord)
    }

    override suspend fun deleteMedRecord(medRecord: MedRecord) {
        medRecordDAO.delete(medRecord)
    }

    override suspend fun replaceTitles(titles: List<ProcedureTitle>) {
        prTitleDAO.deleteAll()
        prTitleDAO.insertAll(titles)
    }

    override suspend fun insertMedRecord(medRecord: MedRecord) {
        medRecordDAO.insert(medRecord)
    }

    override suspend fun removeMedRecord(medRecord: MedRecord) {
        medRecordDAO.delete(medRecord)
    }

    override suspend fun updateMedRecord(medRecord: MedRecord) {
        medRecordDAO.update(medRecord)
    }
}
