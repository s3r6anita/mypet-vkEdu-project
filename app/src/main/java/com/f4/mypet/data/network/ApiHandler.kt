package com.f4.mypet.data.network

import com.f4.mypet.data.network.model.NetworkResult
import com.f4.mypet.data.network.model.response.Response
import com.google.gson.Gson
import retrofit2.HttpException
import java.io.IOException

interface ApiHandler {

    suspend fun <T : Any?> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<Any?> {
        return try {
            val response = execute()
            if (response.data != null) {
                NetworkResult.Success(response.data)
            } else {
                if (response.msg == null) {
                    NetworkResult.Success(null)
                } else {
                    NetworkResult.Error(response.msg)
                }
            }
        } catch (e: HttpException) {
            if (e.code() == serverError) {
                NetworkResult.Error("Сервер недоступен") //"${e.code()} ${e.message()}
            } else {
                val errorResponseBody = e.response()?.errorBody()?.string()
                val errorResponse = Gson().fromJson(errorResponseBody, Response::class.java)
                NetworkResult.Error(errorResponse.msg ?: "Ошибка сериализации ответа сервера")
            }
        } catch (e: IOException) {
            NetworkResult.Error("Превышено время ожидания. Сервер недоступен")
        } catch (e: Throwable) {
            NetworkResult.Error("${e.message}")
        }
    }

    companion object {
        const val serverError = 503
    }

}
