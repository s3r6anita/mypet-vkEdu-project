package com.f4.mypet.ui.screens.profile.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    // если null, то ошибок не было
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
            if (petId != -1) {
                _petUiState.value = repository.getPet(petId)
                // пока подтягивание с сервера отключено
//                _petUiState.value = networkRepository.getPet(petId) ?: _petUiState.value
            }
        }
    }


    fun removePet(pet: Pet) {
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = networkRepository.removePet(pet.id)) {
                is NetworkResult.Success -> {
                    _msg.value = null
                    repository.removePet(pet)
                    repository.removeProceduresForPet(pet.id)
                    repository.removeMedRecordsForPet(pet.id)
                }
                is NetworkResult.Error -> { _msg.value = response.msg }
            }
        }
    }

    fun resetMsg() {
        _msg.value = ""
    }
}
