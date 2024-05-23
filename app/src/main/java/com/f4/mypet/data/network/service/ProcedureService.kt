package com.f4.mypet.data.network.service

import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.network.model.request.CreateProcedureRequest
import com.f4.mypet.data.network.model.response.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ProcedureService {
    @POST("procedure")
    suspend fun createProcedure(@Body procedure: CreateProcedureRequest): Response<String?>

    @GET("procedure")
    suspend fun getProcedures(): Response<List<Procedure>>

    @POST("procedure/update")
    suspend fun updateProcedure(@Body procedure: Procedure): Response<String?>

    @DELETE("procedure/{id}")
    suspend fun removeProcedure(@Path("id") id: Int): Response<Procedure?>

    @GET("procedure/pet/{id}")
    suspend fun getPetProcedures(@Path("id") id: Int): Response<List<Procedure>>
}
