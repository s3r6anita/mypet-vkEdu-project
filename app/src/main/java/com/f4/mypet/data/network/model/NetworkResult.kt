package com.f4.mypet.data.network.model

sealed class NetworkResult<out T : Any?> {
    data class Success<out T : Any?>(val data: T) : NetworkResult<T>()
    data class Error(val msg: String?) : NetworkResult<String>()
}