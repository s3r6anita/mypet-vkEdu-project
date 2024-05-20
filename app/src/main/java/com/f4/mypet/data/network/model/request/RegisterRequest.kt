package com.f4.mypet.data.network.model.request

data class RegisterRequest(
    val email: String,
    val password: String?,
    val name: String,
    val vkid: Long?
)
