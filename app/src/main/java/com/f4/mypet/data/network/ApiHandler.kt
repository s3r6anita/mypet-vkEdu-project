package com.f4.mypet.data.network

import com.f4.mypet.data.network.model.NetworkResult
import com.f4.mypet.data.network.model.response.Response
import retrofit2.HttpException

interface ApiHandler {

    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): NetworkResult<Any> {
        return try {
            val response = execute()
            if (response.data != null) {
                NetworkResult.Success(response)
            } else {
                NetworkResult.Error(response.msg)
            }
        } catch (e: HttpException) {
            NetworkResult.Error("${e.code()} ${e.message()}")
        } catch (e: Throwable) {
            NetworkResult.Exception(e)
        }
    }

}
