package com.f4.mypet.ui.screens.procedure.show

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
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProcedureViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _procedureUiState = MutableStateFlow(
        Procedure(
            0, 0, 0,
            LocalDateTime.parse("01.01.1001 00:00", PetDateTimeFormatter.dateTime),
            "", LocalDateTime.parse("01.01.1001 00:00", PetDateTimeFormatter.dateTime),
            0, 0, 0
        )
    )
    var title = ProcedureTitle(
        name = "",
        type = 0,
    )
    var type = ProcedureType(
        name = "",
        id = title.id
    )

    val procedureUiState = _procedureUiState.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            title = repository.getProcedureTitles()[_procedureUiState.value.title]
            type = repository.getProcedureTypes()[title.type]
        }
    }

    fun getProcedure(procedureId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getProcedure(procedureId).collect { procedure ->
                _procedureUiState.value = procedure
            }
        }

    }
}
