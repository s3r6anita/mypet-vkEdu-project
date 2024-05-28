package com.f4.mypet.ui.screens.medcard.createUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CreateUpdateMedRecordViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _medRecordUiState = MutableStateFlow(
        MedRecord("", LocalDateTime.now(), "", 0)
    )
    val medRecordUiState = _medRecordUiState.asStateFlow()

    init {
        _uiState.update { UIState.Loading }
        _uiState.update { UIState.Success }
    }

    fun getPetMedRecord(medRecordId: Int) {
        if (medRecordId != -1) {
            _uiState.update { UIState.Loading }
            viewModelScope.launch(Dispatchers.IO) {
                repository.getMedRecord(medRecordId).collect { medRecord ->
                    _medRecordUiState.value = medRecord
                }
            }
            _uiState.update { UIState.Success }
        }
    }

    fun addMedRecord(medRecord: MedRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addMedRecord(medRecord)
        }
    }

    fun updateMedRecord(medRecord: MedRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMedRecord(medRecord)
        }
    }

}
