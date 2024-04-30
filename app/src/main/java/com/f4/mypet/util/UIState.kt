package com.f4.mypet.util

sealed class UiState {
    data object Loading : UiState()
    data object Error : UiState()
    data object Success : UiState()
}