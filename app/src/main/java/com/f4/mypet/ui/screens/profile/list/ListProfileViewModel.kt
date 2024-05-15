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
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
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
        viewModelScope.launch(IO) {
            try {
                _petsUiState.value = networkRepository.getPets()
            } catch (e: HttpException) {
                repository.getPets().collect { pets ->
                    _petsUiState.value = pets
                }
            }
        }
        _uiState.update { UIState.Success }
    }

    fun getPetsProfilesFromDB() {
        viewModelScope.launch(IO) {
            repository.getPets().collect { pets ->
                _petsUiState.value = pets
            }
        }
    }
}
