package com.f4.mypet.ui.screens.medcard.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.MedRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class MedRecordViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _medRecordUiState = MutableStateFlow(
        MedRecord(
            title = -1,
            date = LocalDateTime.parse("01.01.1001 00:00", PetDateTimeFormatter.dateTime),
            notes = "",
            pet = 0
        )
    )

    val medRecordUiState = _medRecordUiState.asStateFlow()

    fun getMedRecord(medRecordId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getMedRecord(medRecordId).collect { medRecord ->
                _medRecordUiState.value = medRecord
            }
        }
    }
}
