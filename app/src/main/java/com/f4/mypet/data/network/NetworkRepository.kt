package com.f4.mypet.data.network

import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.network.model.NetworkResult
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.data.network.model.request.RegisterRequest
import com.vk.id.AccessToken

interface NetworkRepository {
    suspend fun saveVKtoken(token: AccessToken)
    suspend fun getVKtoken(): AccessToken
    suspend fun loginByVK(data: RegisterRequest): String?

    suspend fun login(data: LoginRequest): String?
    suspend fun register(data: RegisterRequest): String?

    suspend fun insertPet(pet: Pet): String?
    suspend fun getPets(): List<Pet>
    suspend fun getPet(id: Int): Pet?
    suspend fun updatePet(pet: Pet): String?
    suspend fun removePet(id: Int): NetworkResult<Any?>

    suspend fun insertProcedure(procedure: Procedure): NetworkResult<Any?>
    suspend fun getProcedures(): List<Procedure>
    suspend fun getPetProcedures(id: Int): NetworkResult<Any?>
    suspend fun updateProcedure(procedure: Procedure): NetworkResult<Any?>
    suspend fun removeProcedure(id: Int): NetworkResult<Any?>

//    suspend fun insertProcedure(procedure: Procedure): String?
    suspend fun getMedRecords(): List<MedRecord>
    suspend fun getPetMedRecords(id: Int): NetworkResult<Any?>
//    suspend fun updateProcedure(procedure: Procedure): String?
    suspend fun removeMedRecord(id: Int): NetworkResult<Any?>

    suspend fun insertTitle(title: ProcedureTitle): NetworkResult<Any?>
    suspend fun getTitles(): NetworkResult<Any?>
    suspend fun updateTitle(title: ProcedureTitle): NetworkResult<Any?>
}
