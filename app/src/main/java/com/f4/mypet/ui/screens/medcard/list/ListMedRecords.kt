package com.f4.mypet.ui.screens.medcard.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.ButtonComponent
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.components.PetCardHeader
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground
import kotlinx.coroutines.launch

@Composable
fun ListMedRecords(
    navController: NavHostController,
    profileId: Int,
    canNavigateBack: Boolean,
    viewModel: ListMedRecordsViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPetsMedRecords(profileId)
        }
    }

    val medRecords by viewModel.medRecordsUiState.collectAsState()
    val pet = viewModel.pet

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(id = R.string.medcard_screen_title),
                canNavigateBack = canNavigateBack,
                navigateUp = { navController.navigateUp() },
                actions = { }
            )
        },
        bottomBar = {
            MyPetBottomBar(
                profileId = profileId,
                canNavigateBack = canNavigateBack,
                items = BottomBarData.items,
                getNavController = { navController }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
        ) {
            PetCardHeader(petName = pet.name, backgroundColor = LightBlueBackground)

            // список медзаписей
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                medRecords.forEach { medRecord ->
                    MedRecordItem(
                        medRecord = medRecord,
                        navController = navController
                    )
                }

                // кнопка добавления
                ButtonComponent(
                    onClick = {
                        navController.navigate(Routes.CreateMedRecord.route) {
                            launchSingleTop = true
                        }
                    },
                    text = stringResource(id = R.string.add_button_description),
                    color = ButtonDefaults.buttonColors(containerColor = GreenButton),
                    icon = Icons.Default.Add,
                    modifier = Modifier,
                    textColor = Color.White,
                    borderColor = GreenButton,
                    enabled = true,
                )
            }
        }
    }
}
