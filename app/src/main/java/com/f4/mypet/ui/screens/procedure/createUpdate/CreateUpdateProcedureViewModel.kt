package com.f4.mypet.ui.screens.procedure.createUpdate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Frequency
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType
import com.f4.mypet.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class CreateUpdateProcedureViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    private val _procedureUiState = MutableStateFlow(
        Procedure(
            0, 0, "",0,
            LocalDateTime.now().withMinute(0),
            "", null,
            0, 0
        )
    )
    val procedureUiState = _procedureUiState.asStateFlow()

    var titles = emptyList<ProcedureTitle>() // список всех заголовков
    var types = emptyList<ProcedureType>() // список всех типов
    var frequencyOptions = emptyList<Frequency>() // список вариантов частоты
    var frequencyOptionsTitles = mutableListOf<String>() // список вариантов частоты

    var title = ProcedureTitle(
        // заголовок создаваемой (изменяемой) процедуры
        name = "",
        type = 0,
    )
    var type = ProcedureType( // тип создаваемой (изменяемой) процедуры
        name = "",
        id = title.id
    )

    var frequency = Frequency( // частота создаваемой (изменяемой) процедуры
        option = "никогда",
        frequency = "0"
    )

    init {
        _uiState.update { UIState.Loading }
        viewModelScope.launch(Dispatchers.IO) {
            titles = repository.getProcedureTitlesForCU()
            types = repository.getProcedureTypes()
            frequencyOptions = repository.getFrequencyOptions()
            frequencyOptions.forEach() {
                frequencyOptionsTitles.add(it.id, it.option)
            }
            _uiState.update { UIState.Success }
        }
    }

    fun getPetProcedure(procedureId: Int) {
        if (procedureId != -1) {
            _uiState.update { UIState.Loading }
            viewModelScope.launch(Dispatchers.IO) {
                _procedureUiState.value = repository.getProcedure(procedureId)
                title = titles.find { title -> title.id == _procedureUiState.value.title }
                    ?: title
                type = types.find { type -> type.id == title.type }
                    ?: type
                val frequencyDB = repository.getFrequency(_procedureUiState.value.frequencyOption)
                if (frequencyDB != null) {
                    frequency = frequencyDB
                }

                _uiState.update { UIState.Success }
            }
        } else {
            _uiState.update { UIState.Success }
        }
    }

    fun updateProcedure(procedure: Procedure, title: ProcedureTitle, frequency: Frequency) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProcedure(procedure)
            repository.updateTitle(title)
            repository.updateFrequency(frequency)
        }
    }

    fun createProcedure(procedure: Procedure, title: ProcedureTitle, frequency: Frequency) {
        viewModelScope.launch(Dispatchers.IO) {
            val titleId = repository.insertTitle(title) // start error there
            val procedureWithTitleFreq = procedure.copy(title = titleId)
            repository.insertProcedure(procedureWithTitleFreq)
        }
    }
}
