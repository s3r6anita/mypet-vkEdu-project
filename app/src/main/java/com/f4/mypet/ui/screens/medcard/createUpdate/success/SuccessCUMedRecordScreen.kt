package com.f4.mypet.ui.screens.medcard.createUpdate.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.ui.components.MyPetSnackBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.screens.medcard.createUpdate.CreateUpdateMedRecordViewModel
import com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents.SaveButton
import com.f4.mypet.ui.screens.medcard.createUpdate.success.screenComponents.MedRecordDateField
import com.f4.mypet.ui.screens.medcard.createUpdate.success.screenComponents.MedRecordNotesField
import com.f4.mypet.ui.screens.medcard.createUpdate.success.screenComponents.MedRecordTimeField
import com.f4.mypet.ui.screens.medcard.createUpdate.success.screenComponents.MedRecordTitleField
import kotlinx.coroutines.launch
import java.time.LocalDateTime

@Composable
fun SuccessCUMedCardScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    isCreateScreen: Boolean,
    profileId: Int,
    viewModel: CreateUpdateMedRecordViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val localContext = LocalContext.current

    val medRecordDB by viewModel.medRecordUiState.collectAsState()

    var medRecord by remember {
        mutableStateOf(medRecordDB)
    }

    LaunchedEffect(medRecordDB) {
        medRecord = medRecordDB
    }

    val showIncorrectDateMsg: (e: IllegalArgumentException) -> Unit = {
        scope.launch {
            snackbarHostState.showSnackbar(
                it.message
                    ?: localContext.resources.getString(R.string.incorrect_date)
            )
        }
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(
                    if (isCreateScreen)
                        R.string.cu_therapy_title_create
                    else
                        R.string.cu_therapy_title_update
                ),
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() },
                actions = {}
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) {
                MyPetSnackBar(it.visuals.message)
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 30.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {

            val modifier = Modifier
                .padding(top = 10.dp)
                .fillMaxWidth()
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // название
                    MedRecordTitleField(
                        isCreateScreen = isCreateScreen,
                        onNameChange = { name ->
                            medRecord = medRecord.copy(title = name)

                        },
                        modifier = modifier
                            .padding(bottom = 10.dp),
                        givenTitle = medRecord.title
                    )
                    // дата
                    MedRecordDateField(
                        isCreateScreen = isCreateScreen,
                        modifier = modifier,
                        onDateSelected = { selectedDate ->
                            medRecord = medRecord.copy(
                                date = LocalDateTime.of(
                                    selectedDate.toLocalDate(),
                                    medRecord.date.toLocalTime()
                                )
                            )
                        },
                        onDateIncorrect = showIncorrectDateMsg,
                        dbDate = medRecord.date
                    )
                    MedRecordTimeField(
                        isCreateScreen = isCreateScreen,
                        modifier = modifier,
                        onDateSelected = { selectedTime ->
                            medRecord = medRecord.copy(
                                date = LocalDateTime.of(
                                    medRecord.date.toLocalDate(),
                                    selectedTime.toLocalTime()
                                )
                            )
                        },
                        dbTime = medRecord.date
                    )
                    // заметки
                    MedRecordNotesField(
                        onNotesChange = { notes ->
                            medRecord = medRecord.copy(notes = notes)
                        },
                        modifier = modifier,
                        givenNotes = medRecord.notes
                    )
                }
            }
            // сохранение
            SaveButton(
                save =
                if (isCreateScreen) {
                    {
                        medRecord = medRecord.copy(pet = profileId)
                        scope.launch {
                            viewModel.createMedRecord(medRecord)
                        }
                    }
                } else {
                    {
                        scope.launch {
                            viewModel.updateMedRecord(medRecord)
                        }
                    }
                }
            )
        }
    }
}
