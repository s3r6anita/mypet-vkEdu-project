package com.f4.mypet.ui.screens.profile.createUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import javax.inject.Inject

@HiltViewModel
class CreateUpdateProfileViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _petUiState = MutableStateFlow(
        Pet(
            "", "", "", "Самец",
            LocalDateTime.of(LocalDate.now(), LocalTime.now()),
            "", "", ""
        )
    )
    val petUiState = _petUiState.asStateFlow()
    fun getPetProfile(petId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO: сделать тут try catch на petID
            if (petId != -1) {
                repository.getPet(petId).collect { pet ->
                    _petUiState.value = pet
                }
            }
        }
    }

    fun createPet(pet: Pet) {
        viewModelScope.launch {
            repository.insertPet(pet)
        }
    }

    fun updatePet(pet: Pet) {
        viewModelScope.launch {
            repository.updatePet(pet)
        }
    }
}
