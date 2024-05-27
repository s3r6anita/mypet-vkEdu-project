package com.f4.mypet.ui.screens.profile.show

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.f4.mypet.data.db.Repository
import com.f4.mypet.data.db.entities.Pet
import com.f4.mypet.data.network.NetworkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject

@SuppressWarnings("TooGenericExceptionCaught")
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val repository: Repository,
    private val networkRepository: NetworkRepository,
    @ApplicationContext private val appContext: Context
) : ViewModel() {
    // если null, то ошибок не было
    private val _msg = MutableStateFlow<String?>("")
    val msg = _msg.asStateFlow()

    private val _petUiState = MutableStateFlow(
        Pet(
            "", "", "", "Самец",
            LocalDate.now(),
            "", "", "", -1
        )
    )
    val petUiState = _petUiState.asStateFlow()

    fun getPetProfile(petId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (petId != -1) {
                // получение локально, т.к. данные обновились при получении списка питомцев
                _petUiState.value = repository.getPet(petId)
            }
        }
    }


    fun removePet(pet: Pet) {
        _msg.value = ""
        viewModelScope.launch(Dispatchers.IO) {
//            repository.removePet(pet)
//            repository.removeProceduresForPet(pet.id)
//            repository.removeMedRecordsForPet(pet.id)
            _msg.value = networkRepository.removePet(pet.id)
        }
    }

    fun sharePetInfo(message: String, activityContext: Context) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            putExtra(Intent.EXTRA_TEXT, message)
            type = "text/plain"
        }
        try {
            activityContext.startActivity(
                Intent.createChooser(
                    intent,
                    "Отправить сведения о питомце"
                )
            )
        } catch (e: Exception) {
            Toast.makeText(appContext, "Произошла ошибка", Toast.LENGTH_LONG).show()
        }
    }
}
