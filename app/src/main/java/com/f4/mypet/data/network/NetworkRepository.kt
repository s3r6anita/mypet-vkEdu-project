package com.f4.mypet.data.network

import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
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
    suspend fun removePet(id: Int): String?

    suspend fun insertProcedure(procedure: Procedure): String?
    suspend fun getProcedures(): List<Procedure>
//    suspend fun updateProcedure(procedure: Procedure): String?
//    suspend fun removeProcedure(id: Int): String?

//    suspend fun insertProcedure(procedure: Procedure): String?
    suspend fun getMedRecords(): List<MedRecord>
//    suspend fun updateProcedure(procedure: Procedure): String?
//    suspend fun removeProcedure(id: Int): String?
}
