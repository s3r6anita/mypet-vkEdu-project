package com.f4.mypet.ui.screens.procedure.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.NetworkResult
import com.f4.mypet.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
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

    private val _uiState = MutableStateFlow<UIState>(UIState.Success)
    val uiState = _uiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()


    var pet = Pet(
        "", "", "", "Самец",
        LocalDate.now(),
        "", "", "", 0
    )

    fun getPetProcedures(petId: Int) {
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

    fun refreshProcedures(petId: Int) {
        _uiState.update { UIState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.emit(true)
            val response = networkRepository.getPetProcedures(petId)
            _isRefreshing.emit(false)
            when (response) {
                is NetworkResult.Success -> {
                    _proceduresUiState.value = response.data as List<Procedure>

                    // TODO: update local DB
                    _uiState.update { UIState.Success }
                }
                is NetworkResult.Error -> {
                    _uiState.update { UIState.Error }
                }
            }
        }
    }
}
