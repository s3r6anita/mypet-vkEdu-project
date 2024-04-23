package com.f4.mypet.data.network

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginService {
    @POST("login")
    suspend fun Login(@Body requestBody: LoginRequest): LoginResponse
}