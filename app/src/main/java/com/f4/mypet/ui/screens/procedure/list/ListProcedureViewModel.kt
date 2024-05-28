package com.f4.mypet.ui.screens.procedure.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class ListProcedureViewModel @Inject constructor(
    private val repository: Repository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _proceduresUiState = MutableStateFlow(emptyList<Procedure>())
    val proceduresUiState = _proceduresUiState.asStateFlow()

    private val _titlesUiState = MutableStateFlow(emptyList<ProcedureTitle>())
    val titlesUiState = _titlesUiState.asStateFlow()


    var pet = Pet(
        "", "", "", "Самец",
        LocalDate.now(),
        "", "", "", 0
    )

    fun getPetsProcedures(petId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            pet = repository.getPetForCU(petId)
            repository.getProceduresForPet(petId).collect { procedures ->
                _proceduresUiState.value = procedures
            }
        }
    }

    fun getTitles() {
        viewModelScope.launch {
            repository.getProcedureTitles().collect { titles ->
                _titlesUiState.value = titles
            }
        }
    }
}
