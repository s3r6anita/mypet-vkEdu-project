package com.f4.mypet.data.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.network.authentication.JwtTokenManager
import com.f4.mypet.data.network.model.NetworkResult
import com.f4.mypet.data.network.model.request.CreatePetRequest
import com.f4.mypet.data.network.model.request.CreateProcedureRequest
import com.f4.mypet.data.network.model.request.CreateProcedureTitleRequest
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.data.network.model.request.RegisterRequest
import com.f4.mypet.data.network.model.response.Response
import com.f4.mypet.data.network.service.AuthService
import com.f4.mypet.data.network.service.MedRecordService
import com.f4.mypet.data.network.service.PetService
import com.f4.mypet.data.network.service.ProcedureService
import com.f4.mypet.data.network.service.ProcedureTitleService
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
    private val procedureService: ProcedureService,
    private val medRecordService: MedRecordService,
    private val procedureTitleService: ProcedureTitleService,
    val jwtTokenManager: JwtTokenManager
) : NetworkRepository, ApiHandler {

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

    override suspend fun getPets(): List<Pet> {
        return try {
            petService.getPets().data ?: emptyList()
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }
    }

    override suspend fun getPet(id: Int): Pet? {
        return try {
            petService.getPet(id).data
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }
    }

    override suspend fun insertPet(pet: Pet): String? {
        val request = CreatePetRequest(
            name = pet.name,
            kind = pet.kind,
            breed = pet.breed,
            sex = pet.sex,
            birthday = pet.birthday,
            color = pet.color,
            coat = pet.coat,
            microchipNumber = pet.microchipNumber
        )
        return try {
            val response = petService.createPet(request)
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

    override suspend fun updatePet(pet: Pet): String? {
        return try {
            val response = petService.updatePet(pet)
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

    override suspend fun removePet(id: Int): NetworkResult<Any?> {
        return handleApi { petService.removePet(id) }
    }

    override suspend fun insertProcedure(procedure: Procedure): NetworkResult<Any?> {
        val request = CreateProcedureRequest(
            title = procedure.title,
            isDone = procedure.isDone,
            frequency = procedure.frequency,
            frequencyOption = procedure.frequencyOption,
            dateDone = procedure.dateDone,
            notes = procedure.notes,
            reminder = procedure.reminder,
            pet = procedure.pet,
            inMedCard = procedure.inMedCard
        )
        return handleApi { procedureService.createProcedure(request) }
    }

    override suspend fun getProcedures(): List<Procedure> {
        return try {
            procedureService.getProcedures().data ?: emptyList()
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }
    }

    override suspend fun getPetProcedures(id: Int): NetworkResult<Any?> {
        return handleApi { procedureService.getPetProcedures(id) }
    }

    override suspend fun updateProcedure(procedure: Procedure): NetworkResult<Any?> {
        return handleApi { procedureService.updateProcedure(procedure) }
    }

    override suspend fun removeProcedure(id: Int): NetworkResult<Any?> {
        return handleApi { procedureService.removeProcedure(id) }
    }


    override suspend fun getMedRecords(): List<MedRecord> {
        return try {
            medRecordService.getMedRecords().data ?: emptyList()
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }
    }

    override suspend fun insertTitle(title: ProcedureTitle): NetworkResult<Any?> {
        val request = CreateProcedureTitleRequest(
            name = title.name,
            type = title.type
        )
        return handleApi { procedureTitleService.createTitle(request) }
    }

    override suspend fun getTitles(): List<ProcedureTitle> {
        return try {
            procedureTitleService.getTitles().data ?: emptyList()
        } catch (e: HttpException) {
            throw e
        } catch (e: IOException) {
            throw e
        }
    }

    override suspend fun updateTitle(title: ProcedureTitle): NetworkResult<Any?> {
        return handleApi { procedureTitleService.updatePet(title) }
    }


    companion object {
        const val serverError = 503
        val VK_TOKEN_KEY = stringPreferencesKey("vk_token")

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
    }
}
