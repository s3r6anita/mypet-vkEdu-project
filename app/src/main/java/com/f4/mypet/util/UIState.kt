package com.f4.mypet.util

sealed class UIState {
    data object Loading : UIState()
    data object Error : UIState()
    data object Success : UIState()
}
