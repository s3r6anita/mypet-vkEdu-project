package com.f4.mypet.data.network.service

import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.data.network.model.request.RegisterRequest
import com.f4.mypet.data.network.model.response.LoginRegisterData
import com.f4.mypet.data.network.model.response.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("loginVK")
    suspend fun loginUserByVK(@Body requestBody: RegisterRequest): Response<LoginRegisterData>

    @POST("register")
    suspend fun registerUser(@Body requestBody: RegisterRequest): Response<LoginRegisterData>

    @POST("login")
    suspend fun loginUser(@Body requestBody: LoginRequest): Response<LoginRegisterData>
}
