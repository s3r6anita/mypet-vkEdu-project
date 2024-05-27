package com.f4.mypet.data.network.service

import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.network.model.request.CreateProcedureTitleRequest
import com.f4.mypet.data.network.model.response.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ProcedureTitleService {

    @GET("title")
    suspend fun getTitles(): Response<List<ProcedureTitle>>

    @POST("title")
    suspend fun createTitle(@Body title: CreateProcedureTitleRequest): Response<Int>

    @POST("title/update")
    suspend fun updatePet(@Body title: ProcedureTitle): Response<String?>
}
