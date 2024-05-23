package com.f4.mypet.ui.screens.procedure.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
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
            try {
                _isRefreshing.emit(true)
                _proceduresUiState.value = networkRepository.getPetProcedures(petId)
                _isRefreshing.emit(false)
            } catch (e: HttpException) {
                _uiState.update { UIState.Error }
            } catch (e: IOException) {
                _uiState.update { UIState.Error }
            } finally {
                _uiState.update { UIState.Success }
            }
        }
    }
}
