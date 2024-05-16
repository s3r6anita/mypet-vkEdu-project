package com.f4.mypet.data.network

import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.authentication.JwtTokenManager
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.data.network.model.request.RegisterRequest
import com.f4.mypet.data.network.model.response.Response
import com.f4.mypet.data.network.service.AuthService
import com.f4.mypet.data.network.service.PetService
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
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
            return if (e.code() == serverError) {
                "Сервер недоступен"
            } else {
                val errorResponseBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorResponseBody, Response::class.java)
                errorResponse.msg
            }
        } catch (e: SocketTimeoutException) {
            return "Превышено время ожидания. Сервер недоступен"
        }
    }

    override suspend fun register(data: RegisterRequest): String? {
        try {
            val response = authService.registerUser(data)
            return if (response.data != null) {
                val token = response.data.token
                jwtTokenManager.saveAccessJwt(token)
                null
            } else {
                response.msg
            }
        } catch (e: HttpException) {
            return if (e.code() == serverError) {
                "Сервер недоступен"
            } else {
                val errorResponseBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorResponseBody, Response::class.java)
                errorResponse.msg
            }
        } catch (e: SocketTimeoutException) {
            return "Превышено время ожидания. Сервер недоступен"
        }
    }

    override suspend fun removePet(id: Int): String? {
        return try {
            val response = petService.removePet(id)
            response.data ?: response.msg
        } catch (e: HttpException) {
            if (e.code() == serverError) {
                "Сервер недоступен"
            } else {
                val errorResponseBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorResponseBody, Response::class.java)
                errorResponse.msg ?: "Ошибка сериализации ответа сервера"
            }
        } catch (e: IOException) {
            "Превышено время ожидания. Сервер недоступен"
        }
    }

    override suspend fun getPets(): List<Pet> {
        return try {
            val response = petService.getPets()
            response.data ?: emptyList()
        } catch (e: HttpException) {
            throw e
        }
    }

    companion object {
        const val serverError = 503
    }
}

