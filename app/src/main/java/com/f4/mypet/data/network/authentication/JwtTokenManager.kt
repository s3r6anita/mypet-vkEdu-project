package com.f4.mypet.data.network.authentication

interface JwtTokenManager {
    suspend fun saveAccessJwt(token: String)
    suspend fun getAccessJwt(): String?
    suspend fun clearAllTokens()
    //    suspend fun saveRefreshJwt(token: String)
    //    suspend fun getRefreshJwt(): String?
}
