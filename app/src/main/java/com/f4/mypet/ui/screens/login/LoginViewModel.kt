package com.f4.mypet.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.request.LoginRequest
import com.f4.mypet.util.UiState
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

    private val _msg = MutableStateFlow<String?>("")
    val msg = _msg.asStateFlow()
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)
        viewModelScope.launch {
            _msg.value = networkRepository.login(loginRequest)
            if (_msg.value == null )
                _uiState.update { UiState.Success }
            else
                _uiState.update { UiState.Error }
        }
    }
}
