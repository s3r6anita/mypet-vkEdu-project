package com.f4.mypet.ui.screens.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.util.UIState
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiManager
import com.vk.api.sdk.internal.ApiCommand
import com.vk.dto.common.id.UserId
import com.vk.id.AccessToken
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.concurrent.LinkedBlockingDeque
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {

    private val _msg = MutableStateFlow<String?>(null)
    val msg = _msg.asStateFlow()
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        _uiState.update { UIState.Loading }
        val loginRequest = LoginRequest(email, password)
        viewModelScope.launch {
            _msg.value = networkRepository.login(loginRequest)
            if (_msg.value == null )
                _uiState.update { UIState.Success }
            else
                _uiState.update { UIState.Error }
        }
    }

}

fun <T> ApiCommand<T>.withVKIDToken(
    accessToken: AccessToken

): ApiCommand<T> {
    return object : ApiCommand<T>() {

        private fun saveToken(token: AccessToken) {
            VK.saveAccessToken(
                userId = UserId(token.userID),
                accessToken = token.token,
                secret = null,
                expiresInSec = ((System.currentTimeMillis() - token.expireTime) / 1000).toInt(),
                createdMs = System.currentTimeMillis()
            )
        }

        override fun onExecute(manager: VKApiManager): T {
            try {
                saveToken(accessToken)
                return this@withVKIDToken.execute(manager)
            } catch (e: Exception) {
                Log.e("TAG", e.stackTraceToString())
                val res = LinkedBlockingDeque<Result<T>>(1)
                return res.take().getOrThrow()
            }
        }
    }
}