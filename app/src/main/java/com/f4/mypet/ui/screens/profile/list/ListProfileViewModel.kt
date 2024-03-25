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
    private val _petsUiState = MutableStateFlow(emptyList<Pet>())
    val petsUiState = _petsUiState.asStateFlow()

    fun getPetsProfiles() {
        viewModelScope.launch(IO) {
            repository.getPets().collect() { pets ->
                _petsUiState.value = pets
            }
        }

    }
}