package com.f4.mypet.data.network

import com.f4.mypet.data.network.authentication.JwtTokenManager
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.data.network.service.AuthService
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val jwtTokenManager: JwtTokenManager
) : NetworkRepository {

    override suspend fun login(data: LoginRequest): String? {
        val response = authService.loginUser(data)
        if (response.data != null) {
            val token = response.data.token
            jwtTokenManager.saveAccessJwt(token)
            return null
        }
        else {
            return response.msg
        }
    }


//    companion object RetrofitClient {
//        private val baseUrl = "http://mypet-backend-s3r6.amvera.io/"
//
//        private val retrofit: Retrofit = Retrofit.Builder()
//            .addConverterFactory(GsonConverterFactory.create())
//            .baseUrl(baseUrl)
//            .build()
//
//        fun <T> createService(serviceClass: Class<T>): T {
//            return retrofit.create(serviceClass)
//        }
//    }
}

