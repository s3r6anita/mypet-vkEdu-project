package com.f4.mypet.data.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.authentication.JwtTokenManager
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.data.network.model.request.RegisterRequest
import com.f4.mypet.data.network.model.response.Response
import com.f4.mypet.data.network.service.AuthService
import com.f4.mypet.data.network.service.PetService
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.vk.id.AccessToken
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
    private val authService: AuthService,
    private val petService: PetService,
    private val jwtTokenManager: JwtTokenManager
) : NetworkRepository {

    override suspend fun saveVKtoken(token: AccessToken) {
        val jsonFromToken = GsonSerializer.toJson(token)
        dataStore.edit { preferences ->
            preferences[VK_TOKEN_KEY] = jsonFromToken
        }
    }

    override suspend fun getVKtoken(): AccessToken {
        val tokenAsJson = dataStore.data.map { preferences ->
            preferences[VK_TOKEN_KEY]
        }.first() ?: ""
        return GsonSerializer.fromJson<AccessToken>(tokenAsJson)
    }


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
        } catch (e: IOException) {
            return "Превышено время ожидания. Сервер недоступен"
        }
    }

    override suspend fun loginByVK(data: RegisterRequest): String? {
        try {
            val response = authService.loginUserByVK(data)
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
        } catch (e: IOException) {
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
        } catch (e: IOException) {
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
            petService.getPets().data ?: emptyList()
        } catch (e: HttpException) {
            throw e
        }
    }

    companion object {
        const val serverError = 503
        val VK_TOKEN_KEY = stringPreferencesKey("vk_token")

    }
}

object GsonSerializer {
    val gson = Gson()

    fun <T> toJson(obj: T): String {
        return gson.toJson(obj)
    }

    inline fun <reified T> fromJson(json: String): T {
        val type = object : TypeToken<T>() {}.type
        return gson.fromJson(json, type)
    }
}