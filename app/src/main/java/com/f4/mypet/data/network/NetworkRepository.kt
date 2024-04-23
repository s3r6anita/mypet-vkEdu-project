package com.f4.mypet.data.network

class NetworkRepository(
    private val Service: LoginService
) {
    suspend fun login(email: String, password: String) = LoginService.login()
}