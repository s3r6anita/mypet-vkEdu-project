package com.f4.mypet.ui.screens.profile.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _petUiState = MutableStateFlow(emptyList<Pet>())
    val petUiState = _petUiState.asStateFlow()

    fun getPetsProfiles() {
        viewModelScope.launch(IO) {
            repository.getPets().collect() { pets ->
                _petUiState.value = pets
            }
        }

    }
}