package com.f4.mypet.ui.screens.profile.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.NetworkRepository
import com.f4.mypet.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
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
    private val _petsUiState = MutableStateFlow(emptyList<Pet>())
    val petsUiState = _petsUiState.asStateFlow()

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getPetsProfiles() {
        _uiState.update { UIState.Loading }
        viewModelScope.launch(IO) {
            try {
                networkRepository.getPets().flowOn(IO).onEach {
                   _petsUiState.value = it
                    repository.replaceAllData(_petsUiState.value)
                   _uiState.update { UIState.Success }
                }.launchIn(viewModelScope)
                // TODO: получать с сервера процедуры и медрекорды и сразу отправлять в локалку
            } catch (e: HttpException) {
                _petsUiState.value = repository.getPets()
            } catch (e: IOException) {
                _petsUiState.value = repository.getPets()
            }
        }
    }
}
