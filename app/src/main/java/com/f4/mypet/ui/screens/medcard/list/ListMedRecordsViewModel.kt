package com.f4.mypet.ui.screens.medcard.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.db.entities.Pet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ListMedRecordsViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _medRecordsUiState = MutableStateFlow(emptyList<MedRecord>())
    val medRecordsUiState = _medRecordsUiState.asStateFlow()

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
}
