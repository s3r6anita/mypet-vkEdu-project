package com.f4.mypet.ui.screens.medcard.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ListMedRecordsViewModel @Inject constructor(
    private val repository: Repository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _medRecordsUiState = MutableStateFlow(emptyList<MedRecord>())
    val medRecordsUiState = _medRecordsUiState.asStateFlow()
    private val _msg = MutableStateFlow("")
    val msg = _msg.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    var pet = Pet(
        "", "", "", "Самец",
        LocalDate.now(),
        "", "", "", 0
    )

    fun getPetsMedRecords(petId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            pet = repository.getPetForCU(petId)
            repository.getMedRecordsForPet(petId).collect { medRecords ->
                _medRecordsUiState.value = medRecords
            }
        }
    }

    fun refreshMedRecords(petId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.emit(true)
            when (val response = networkRepository.getPetMedRecords(petId)) {
                is NetworkResult.Success -> {
                    _medRecordsUiState.value = response.data as List<MedRecord>
                    // обновление локальной БД
                    repository.removeMedRecordsForPet(petId)
                    repository.insertListOfMedRecords(_medRecordsUiState.value)
                }
                is NetworkResult.Error -> {_msg.update { response.msg ?: "Error" } }
            }
            _isRefreshing.emit(false)
        }
    }

    fun resetMsg() {
        _msg.value = ""
    }
}
