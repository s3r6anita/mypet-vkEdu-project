package com.f4.mypet.ui.screens.auth.login

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.data.network.model.request.RegisterRequest
import com.f4.mypet.util.UIState
import com.vk.id.AccessToken
import com.vk.id.OAuth
import com.vk.id.VKIDAuthFail
import com.vk.id.onetap.common.OneTapOAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
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
            if (_msg.value == null)
                _uiState.update { UIState.Success }
            else
                _uiState.update { UIState.Error }
        }
    }

    fun loginByVK(token: AccessToken) {
        _uiState.update { UIState.Loading }
        val loginByVKRequest = RegisterRequest(
            email = token.userData.email ?: token.userData.phone ?: "",
            password = null,
            name = token.userData.firstName,
            vkid = token.userID
        )
        viewModelScope.launch {
            _msg.value = networkRepository.loginByVK(loginByVKRequest)
            if (_msg.value == null)
                _uiState.update { UIState.Success }
            else
                _uiState.update { UIState.Error }
        }
    }

    fun saveToken(token: AccessToken) {
        viewModelScope.launch {
            networkRepository.saveVKtoken(token)
        }
    }

    fun getOneTapSuccessCallback(
        onToken: (AccessToken) -> Unit
    ): (OneTapOAuth?, AccessToken) -> Unit = { _, token ->
        onToken(token)
    }

    fun getOneTapFailCallback(
        context: Context
    ): (OneTapOAuth?, VKIDAuthFail) -> Unit = { oAuth, fail ->
        onVKIDAuthFail(context, oAuth?.toOAuth(), fail)
    }

    companion object {

        private var currentToast: Toast? = null

        private fun showToast(context: Context, text: String) {
            currentToast?.cancel()
            currentToast = Toast.makeText(context, text, Toast.LENGTH_LONG)
            currentToast?.show()
        }

        private fun onVKIDAuthFail(
            context: Context,
            oAuth: OAuth?,
            fail: VKIDAuthFail,
        ) {
            val oAuthLabel = oAuth?.name ?: "VK ID"
            when (fail) {
                is VKIDAuthFail.Canceled -> {
                    showToast(context, "Auth with $oAuthLabel was canceled")
                }

                else -> {
                    showToast(context, "Auth with $oAuthLabel failed with: ${fail.description}")
                }

            }
        }
    }
}
