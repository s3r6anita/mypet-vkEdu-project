package com.f4.mypet.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkRepository(
    private val Service: LoginService
) {
    suspend fun login(email: String, password: String) = LoginRetrofitService
// TODO

    private val baseUrl = "http://mypet-backend-s3r6.amvera.io/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val LoginRetrofitService: LoginService by lazy {
        retrofit.create(LoginService::class.java)
    }

    val Repository: NetworkRepository by lazy {
        NetworkRepository(LoginRetrofitService)
    }
}