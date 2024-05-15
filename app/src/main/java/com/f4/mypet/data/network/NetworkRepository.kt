package com.f4.mypet.data.network

import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.model.request.LoginRequest

interface NetworkRepository {
    suspend fun login(data: LoginRequest): String?
//    suspend fun register(data: RegisterRequest): Response<LoginRegisterData>

    suspend fun getPets(): List<Pet>
}
