package com.f4.mypet.ui.screens.newsfeed

import androidx.lifecycle.ViewModel
import com.f4.mypet.data.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PetsNewsScreenViewModel @Inject constructor(
    networkRepository: NetworkRepository
) : ViewModel() {

}