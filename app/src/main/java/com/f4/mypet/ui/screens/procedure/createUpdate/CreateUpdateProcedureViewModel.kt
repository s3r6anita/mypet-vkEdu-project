package com.f4.mypet.ui.screens.procedure.createUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.PetDateTimeFormatter
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

sealed class UiState {
    data object Loading : UiState()
    data class Error(val e: Exception) : UiState()
    data object Success : UiState()
}

@HiltViewModel
class CreateUpdateProcedureViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _procedureUiState = MutableStateFlow(
        Procedure(
            0, 0, 0,
            LocalDateTime.parse("01.01.1001 00:00", PetDateTimeFormatter.dateTime),
            "", LocalDateTime.parse("01.01.1001 00:00", PetDateTimeFormatter.dateTime),
            0, 0, 0
        )
    )
    val procedureUiState = _procedureUiState.asStateFlow()

    var titles = emptyList<ProcedureTitle>() // список всех заголовков
    var types = emptyList<ProcedureType>() // список всех типов

    var title = ProcedureTitle( // заголовок создаваемой (изменяемой) процедуры
        name = "Неизвестно",
        type = 0,
    )
    var type = ProcedureType( // тип создаваемой (изменяемой) процедуры
        name = "Неизвестно",
        id = title.id
    )

    init {
        viewModelScope.launch(Dispatchers.IO) {
            titles = repository.getProcedureTitles()
            types = repository.getProcedureTypes()
            _uiState.update { UiState.Success }
        }
    }

    fun getPetProcedure(procedureId: Int) {
        if (procedureId != -1) {
            viewModelScope.launch(Dispatchers.IO) {
                repository.getProcedure(procedureId).collect { procedure ->
                    _procedureUiState.value = procedure
                    title = titles.find { title -> title.id == procedure.title }
                        ?: title
                    type = types.find { type -> type.id == title.type }
                        ?:  type
                }
                _uiState.update { UiState.Success }
            }
        }
    }
}
