package com.f4.mypet.data.network

import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.data.network.model.request.RegisterRequest
import com.vk.id.AccessToken

interface NetworkRepository {
    suspend fun saveVKtoken(token: AccessToken)
    suspend fun getVKtoken(): AccessToken
    suspend fun loginByVK(data: RegisterRequest): String?

    suspend fun login(data: LoginRequest): String?
    suspend fun register(data: RegisterRequest): String?

    suspend fun getPets(): List<Pet>
//    suspend fun insertPet(): List<Pet>
//    suspend fun updatePet(): List<Pet>
    suspend fun removePet(id: Int): String?
}
