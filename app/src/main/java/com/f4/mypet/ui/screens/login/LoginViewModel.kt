package com.f4.mypet.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.request.LoginRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: NetworkRepository
): ViewModel() {
    var msg: String? = null

    fun login(email: String, password: String): String? {
        val loginRequest = LoginRequest(email, password)
        viewModelScope.launch {
            msg = repository.login(loginRequest)
        }
        return msg
    }

}