package com.f4.mypet.data.network.service

import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.network.model.request.CreateMedRecordRequest
import com.f4.mypet.data.network.model.response.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MedRecordService {
    @POST("med")
    suspend fun createMedRecord(@Body medRecord: CreateMedRecordRequest): Response<String?>

    @GET("med")
    suspend fun getMedRecords(): Response<List<MedRecord>>

    @POST("med/update")
    suspend fun updateMedRecord(@Body procedure: MedRecord): Response<String?>

    @DELETE("med/{id}")
    suspend fun removeMedRecord(@Path("id") id: Int): Response<String?>
}
