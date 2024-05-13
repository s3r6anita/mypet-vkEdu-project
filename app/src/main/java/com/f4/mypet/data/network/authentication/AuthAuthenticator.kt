package com.f4.mypet.data.network.authentication

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import javax.inject.Inject

class AuthAuthenticator @Inject constructor(
    private val tokenManager: JwtTokenManager,
) : Authenticator {
    companion object {
        const val HEADER_AUTHORIZATION = "Authorization"
        const val TOKEN_TYPE = "Bearer"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val currentToken = runBlocking {
            tokenManager.getAccessJwt()
        }

        // TODO: можно добавить обработку response.code == 401, чтобы выполнялся поход за новым токеном как в https://notificare.com/blog/2023/04/21/android-retrofit-refresh-authentication/

        return if (currentToken != null) response.request.newBuilder()
            .header(HEADER_AUTHORIZATION, "$TOKEN_TYPE $currentToken")
            .build()
        else null
    }
}
