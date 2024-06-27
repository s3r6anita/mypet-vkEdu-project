package com.f4.mypet.ui.screens.profile.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.MedRecord
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.data.db.entities.ProcedureTitle
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.data.network.model.NetworkResult
import com.f4.mypet.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class ListProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val networkRepository: NetworkRepository
) : ViewModel() {
    private val _proceduresUiState = MutableStateFlow(emptyList<Procedure>())
    private val _procedureTitlesUiState = MutableStateFlow(emptyList<ProcedureTitle>())
    private val _medRecordsUiState = MutableStateFlow(emptyList<MedRecord>())
    private val _petsUiState = MutableStateFlow(emptyList<Pet>())
    val petsUiState = _petsUiState.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPetsProfiles() {
        _uiState.update { UIState.Loading }
        viewModelScope.launch(IO) {
            try {
                _petsUiState.value = networkRepository.getPets()
                _proceduresUiState.value = networkRepository.getProcedures()
                _medRecordsUiState.value = networkRepository.getMedRecords()
                when (val titles = networkRepository.getTitles()) {
                    is NetworkResult.Success -> _procedureTitlesUiState.value = titles.data as List<ProcedureTitle>
                    is NetworkResult.Error -> _uiState.update { UIState.Error }
                }

                repository.replaceAllData(_petsUiState.value, _proceduresUiState.value, _medRecordsUiState.value,  _procedureTitlesUiState.value)
            } catch (e: HttpException) {
                _petsUiState.value = repository.getPets()
                _uiState.update { UIState.Error }
            } catch (e: IOException) {
                _petsUiState.value = repository.getPets()
                _uiState.update { UIState.Error }
            } finally {
                _uiState.update { UIState.Success }
            }
        }
    }
}
