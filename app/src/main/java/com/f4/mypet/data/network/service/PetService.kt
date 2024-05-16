package com.f4.mypet.data.network.service

import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.model.response.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface PetService {
    @GET("pet")
    suspend fun getPets(): Response<List<Pet>>

    @DELETE("pet/{id}")
    suspend fun removePet(@Path("id") id: Int): Response<String?>
}
