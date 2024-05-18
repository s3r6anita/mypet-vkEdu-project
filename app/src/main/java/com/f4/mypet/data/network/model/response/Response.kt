package com.f4.mypet.data.network.model.response

data class Response<T>(
    val data: T?,
    val msg: String?
)
