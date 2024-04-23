package com.f4.mypet.data.network

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val data: LoginData,
    val hash: String,
    val statusCode: StatusCode
)

data class LoginData(
    val email: String,
    val password: String,
    val name: String
)

data class StatusCode(
    val value: Int,
    val description: String
)