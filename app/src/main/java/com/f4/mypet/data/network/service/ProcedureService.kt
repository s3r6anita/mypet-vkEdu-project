package com.f4.mypet.data.network.service

import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.network.model.response.Response
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Path

interface ProcedureService: MyPetService {
    @GET("procedure")
    suspend fun getProcedures(): Response<List<Procedure>>

    @DELETE("procedure/{id}")
    suspend fun removeProcedure(@Path("id") id: Int): Response<Procedure?>
}