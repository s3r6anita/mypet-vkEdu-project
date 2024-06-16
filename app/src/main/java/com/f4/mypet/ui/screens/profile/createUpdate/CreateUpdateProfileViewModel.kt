package com.f4.mypet.ui.screens.profile.createUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class CreateUpdateProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _msg = MutableStateFlow<String?>("")
    val msg = _msg.asStateFlow()
    private val _petUiState = MutableStateFlow(
        Pet(
            "", "", "", "Самец",
            LocalDate.now(),
            "", "", "", -1
        )
    )
    val petUiState = _petUiState.asStateFlow()

    fun getPetProfile(petId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO: сделать тут try catch на petID
            if (petId != -1) {
                _petUiState.value = repository.getPet(petId)
            }
        }
    }

    fun createPet(pet: Pet) {
        _msg.value = ""
        viewModelScope.launch {
            _msg.value = networkRepository.insertPet(pet)
            repository.insertPet(pet)
        }
    }

    fun updatePet(pet: Pet) {
        _msg.value = ""
        viewModelScope.launch {
            _msg.value = networkRepository.updatePet(pet)
            repository.updatePet(pet)
        }
    }

    fun resetMsg() {
        _msg.value = ""
    }
}
