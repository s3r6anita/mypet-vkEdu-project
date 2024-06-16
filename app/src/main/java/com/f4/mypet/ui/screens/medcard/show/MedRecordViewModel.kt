package com.f4.mypet.ui.screens.medcard.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.NetworkResult
import com.f4.mypet.util.PetDateTimeFormatter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MedRecordViewModel @Inject constructor(
    private val repository: Repository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _medRecordUiState = MutableStateFlow(
        MedRecord(
            title = "",
            date = LocalDateTime.parse("01.01.1001 00:00", PetDateTimeFormatter.dateTime),
            notes = "",
            pet = 0,
            id = -1
        )
    )
    val medRecordUiState = _medRecordUiState.asStateFlow()

    // если null, то ошибок при удалении не было
    private val _msg = MutableStateFlow<String?>("")
    val msg = _msg.asStateFlow()

    fun getMedRecord(medRecordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _medRecordUiState.value = repository.getMedRecord(medRecordId)
        }
    }

    fun deleteMedRecord(medRecord: MedRecord){
        viewModelScope.launch(Dispatchers.IO) {
            when (val response = networkRepository.removeMedRecord(medRecord.id)) {
                is NetworkResult.Success -> {
                    _msg.value = null
                    repository.deleteMedRecord(medRecord)
                }
                is NetworkResult.Error -> { _msg.value = response.msg }
            }
        }
    }

    fun resetMsg() {
        _msg.value = ""
    }
}
