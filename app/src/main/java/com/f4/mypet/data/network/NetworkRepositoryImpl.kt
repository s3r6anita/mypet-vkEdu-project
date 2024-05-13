package com.f4.mypet.data.network

import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.authentication.JwtTokenManager
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.data.network.model.response.Response
import com.f4.mypet.data.network.service.AuthService
import com.f4.mypet.data.network.service.PetService
import com.google.gson.Gson
import retrofit2.HttpException
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val authService: AuthService,
    private val petService: PetService,
    private val jwtTokenManager: JwtTokenManager
) : NetworkRepository {

    override suspend fun login(data: LoginRequest): String? {
        try {
            val response = authService.loginUser(data)
            return if (response.data != null) {
                val token = response.data.token
                jwtTokenManager.saveAccessJwt(token)
                null
            } else {
                response.msg
            }
        } catch (e: HttpException) {
            if (e.code() != 503) {
                val errorResponseBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorResponseBody, Response::class.java)
                return errorResponse.msg
            } else
                return "Сервер не доступен"
        } catch (e: Throwable) {
            return e.message
        }
    }

    override suspend fun getPets(): List<Pet> {
        try {
            val response = petService.getPets()
            return response.data ?: emptyList()
        } catch (e: HttpException) {
            return emptyList()
        }
    }
}

