package com.f4.mypet.data.network

import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("/login")
    suspend fun Login(@Body requestBody: RequestBody): Response<ResponseBody>
}