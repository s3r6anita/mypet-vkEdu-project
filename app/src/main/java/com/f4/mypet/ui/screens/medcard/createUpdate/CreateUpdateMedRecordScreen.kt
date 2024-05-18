package com.f4.mypet.ui.screens.medcard.createUpdate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents.SaveButton
import com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents.TherapyDateField
import com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents.TherapyNameField
import com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents.TherapyNotesField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUpdateMedRecordScreen(
    navController: NavHostController,
    profileId: Int,
    isCreateScreen: Boolean
) {
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
            Box() {
                Column(
                    modifier = Modifier
                        //.padding(20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    // название
                    TherapyNameField(
                        isCreateScreen = isCreateScreen,
                        onNameChange = { name ->
                            // Обработка изменений в названии терапии
                        },
                        modifier = modifier
                            .padding(bottom = 10.dp),
                    )
                    // дата
                    TherapyDateField(
                        isCreateScreen = isCreateScreen,
                        modifier = modifier,
                        onDateSelected = { selectedDate ->
                            // Обработка выбранной даты
                        }
                    )
                    // заметки
                    TherapyNotesField(
                        isCreateScreen = isCreateScreen,
                        onNotesChange = { notes ->
                            // Обработка изменений в заметках
                        },
                        modifier = modifier
                    )
                }
            }
            // сохранение
            SaveButton()
        }
    }
}
