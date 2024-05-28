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

                // TODO: когда будешь делать получение записей с сервера добавь это для обновление локальной бд
//                repository.removeMedRecordsForPet(petId)
//                repository.insertListOfProcedures(_medRecordsUiState.value)

            }
        }
    }

//    fun refreshMedRecords(petId: Int) {
//        _uiState.update { UIState.Loading }
//        viewModelScope.launch(Dispatchers.IO) {
//            _isRefreshing.emit(true)
//            val response = networkRepository.getPetProcedures(petId)
//            _isRefreshing.emit(false)
//            when (response) {
//                is NetworkResult.Success -> {
//                    _proceduresUiState.value = response.data as List<Procedure>
//                    // обновление локальной БД
//                    repository.removeProceduresForPet(petId)
//                    repository.insertListOfProcedures(_proceduresUiState.value)
//                    when (val titles = networkRepository.getTitles()) {
//                        is NetworkResult.Success -> {
//                            _titlesUiState.value = titles.data as List<ProcedureTitle>
//                            repository.replaceTitles(_titlesUiState.value)
//                            _uiState.update { UIState.Success }
//                        }
//                        is NetworkResult.Error -> _uiState.update { UIState.Error }
//                    }
//                }
//                is NetworkResult.Error -> {
//                    _uiState.update { UIState.Error }
//                }
//            }
//        }
//    }
}
