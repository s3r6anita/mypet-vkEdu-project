package com.f4.mypet.data.network.service

import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.model.response.Response
import retrofit2.http.GET

interface PetService {
    @GET("pet/")
    suspend fun getPets(): Response<List<Pet>>
}