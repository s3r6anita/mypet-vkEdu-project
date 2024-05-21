package com.f4.mypet.ui.screens.procedure.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Frequency
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType
import com.f4.mypet.ui.screens.procedure.FrequencyOptions
import com.f4.mypet.util.PetDateTimeFormatter
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
            0, 0, "",0,
            LocalDateTime.parse("01.01.1001 00:00", PetDateTimeFormatter.dateTime),
            "", LocalDateTime.parse("01.01.1001 00:00", PetDateTimeFormatter.dateTime),
            0, 0, 0
        )
    )
    var title = ProcedureTitle(
        name = "Неизвестно",
        type = -1,
        id = -1
    )
    var type = ProcedureType(
        name = "Неизвестно",
        id = title.type
    )
    var frequency = Frequency(
        "Никогда",
        "0"
    )

    val procedureUiState = _procedureUiState.asStateFlow()

    fun getProcedure(procedureId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _procedureUiState.value = repository.getProcedure(procedureId)
            title =
                repository.getProcedureTitlesForCU().find { it.id == _procedureUiState.value.title }
                    ?: title
            type = repository.getProcedureTypes().find { it.id == title.type }
                ?: type
            val frequencyDB = repository.getFrequency(_procedureUiState.value.frequencyOption)
            if (frequencyDB != null) {
                frequency = frequencyDB
            }

            when (frequency.option) {
                FrequencyOptions.Minutes.period -> frequency.frequency =
                    _procedureUiState.value.frequency + FrequencyOptions.Minutes.abbreviation
                FrequencyOptions.Hours.period -> frequency.frequency =
                    _procedureUiState.value.frequency + FrequencyOptions.Hours.abbreviation
                FrequencyOptions.Days.period -> frequency.frequency =
                    _procedureUiState.value.frequency + FrequencyOptions.Days.abbreviation
                FrequencyOptions.Weeks.period -> frequency.frequency =
                    _procedureUiState.value.frequency + FrequencyOptions.Weeks.abbreviation
                else -> frequency.frequency = FrequencyOptions.Never.abbreviation
            }
        }
    }

    fun deleteProcedure(procedure: Procedure) {
        viewModelScope.launch(Dispatchers.IO) {
            //TODO add delete of frequency
            repository.deleteProcedure(procedure)
        }
    }
}
