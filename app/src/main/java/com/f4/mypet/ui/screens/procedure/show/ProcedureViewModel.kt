package com.f4.mypet.ui.screens.procedure.show

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Frequency
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.db.entities.ProcedureType
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.NetworkResult
import com.f4.mypet.ui.screens.procedure.FrequencyOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class ProcedureViewModel @Inject constructor(
    private val repository: Repository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _procedureUiState = MutableStateFlow(
        Procedure(
            0, 0, "", 0,
            LocalDateTime.now().withMinute(0),
            "", LocalDateTime.now().withMinute(0),
            0, 0, 0
        )
    )
    val procedureUiState = _procedureUiState.asStateFlow()
    // если null, то ошибок при удалении не было
    private val _msg = MutableStateFlow<String?>("")
    val msg = _msg.asStateFlow()

    var title = ProcedureTitle(name = "", type = -1, id = -1)
    var type = ProcedureType(name = "", id = title.type)
    var frequency = Frequency(option = "Никогда", frequency = "0")

    fun getProcedure(procedureId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _procedureUiState.value = repository.getProcedure(procedureId)
            frequency = repository.getFrequency(_procedureUiState.value.frequencyOption)
            title = repository.getProcedureTitlesForCU()
                .find { it.id == _procedureUiState.value.title } ?: title
            type = repository.getProcedureTypes()
                .find { it.id == title.type } ?: type

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
             when (val response = networkRepository.removeProcedure(procedure.id)) {
                is NetworkResult.Success -> {
                    _msg.value = null
                    repository.deleteProcedure(procedure)
                }
                is NetworkResult.Error -> { _msg.value = response.msg }
            }
        }
    }
}

