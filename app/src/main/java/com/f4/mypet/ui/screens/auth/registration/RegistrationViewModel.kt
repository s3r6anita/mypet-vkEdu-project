package com.f4.mypet.ui.screens.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.request.RegisterRequest
import com.f4.mypet.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _msg = MutableStateFlow<String?>(null)
    val msg = _msg.asStateFlow()
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    fun register(email: String, password: String, name: String) {
        _uiState.update { UIState.Loading }
        val registerRequest = RegisterRequest(email, password, name)
        viewModelScope.launch {
            _msg.value = networkRepository.register(registerRequest)
            if (_msg.value == null )
                _uiState.update { UIState.Success }
            else
                _uiState.update { UIState.Error }
        }
    }
}
