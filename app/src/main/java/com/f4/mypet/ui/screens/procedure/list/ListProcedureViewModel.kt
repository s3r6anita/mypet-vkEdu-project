package com.f4.mypet.ui.screens.procedure.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
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
class ListProcedureViewModel @Inject constructor(
    private val repository: Repository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _proceduresUiState = MutableStateFlow(emptyList<Procedure>())
    val proceduresUiState = _proceduresUiState.asStateFlow()

    private val _titlesUiState = MutableStateFlow(emptyList<ProcedureTitle>())
    val titlesUiState = _titlesUiState.asStateFlow()

    private val _msg = MutableStateFlow("")
    val msg = _msg.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    var pet = Pet(
        "", "", "", "Самец",
        LocalDate.now(),
        "", "", "", -1
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
        viewModelScope.launch(Dispatchers.IO) {
            _isRefreshing.emit(true)
            when (val response = networkRepository.getPetProcedures(petId)) {
                is NetworkResult.Success -> {
                    _proceduresUiState.value = response.data as List<Procedure>
                    // обновление локальной БД
                    repository.removeProceduresForPet(petId)
                    repository.insertListOfProcedures(_proceduresUiState.value)
                    when (val titles = networkRepository.getTitles()) {
                        is NetworkResult.Success -> {
                            _titlesUiState.value = titles.data as List<ProcedureTitle>
                            repository.replaceTitles(_titlesUiState.value)
                        }
                        is NetworkResult.Error -> _msg.update { titles.msg ?: "Error" }
                    }
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
