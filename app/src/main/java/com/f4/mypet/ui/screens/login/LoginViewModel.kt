package com.f4.mypet.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val repository: NetworkRepository
): ViewModel() {
    fun login(email: String, password: String) {
        viewModelScope.launch {
            repository.login(email, password)
        }
    }
}