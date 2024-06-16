package com.f4.mypet.data.network.service

import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.model.request.CreatePetRequest
import com.f4.mypet.data.network.model.response.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface PetService {
    @POST("pet")
    suspend fun createPet(@Body pet: CreatePetRequest): Response<String?>

    @GET("pet/{id}")
    suspend fun getPet(@Path("id") id: Int): Response<Pet>

    @GET("pet")
    suspend fun getPets(): Response<List<Pet>>

    @POST("pet/update")
    suspend fun updatePet(@Body pet: Pet): Response<String?>

    @DELETE("pet/{id}")
    suspend fun removePet(@Path("id") id: Int): Response<String?>
}
