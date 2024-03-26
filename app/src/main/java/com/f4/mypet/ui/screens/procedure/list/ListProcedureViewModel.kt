package com.f4.mypet.ui.screens.procedure.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
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
class ListProcedureViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _proceduresUiState = MutableStateFlow(emptyList<Procedure>())
    val proceduresUiState = _proceduresUiState.asStateFlow()

    var titles = emptyList<ProcedureTitle>()

    var pet = Pet(
        "", "", "", "Самец",
        LocalDateTime.of(LocalDate.now(), LocalTime.now()),
        "", "", "", 0
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            titles = repository.getProcedureTitles()
        }
    }

    fun getPetsProcedures(petId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            pet = repository.getPetForCU(petId)
            repository.getProceduresForPet(petId).collect() { procedures ->
                _proceduresUiState.value = procedures
            }
        }
    }
}