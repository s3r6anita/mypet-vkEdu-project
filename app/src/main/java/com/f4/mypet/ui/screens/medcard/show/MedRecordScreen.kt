package com.f4.mypet.ui.screens.medcard.show

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.screens.medcard.show.screenComponents.RemoveMedRecordAlert
import com.f4.mypet.ui.screens.medcard.show.screenComponents.ShowMedRecordData
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.RedButton
import kotlinx.coroutines.launch

@Composable
fun MedRecordScreen(
    navController: NavHostController,
    medRecordId: Int,
    viewModel: MedRecordViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getMedRecord(medRecordId)
        }
    }
    val medRecord by viewModel.medRecordUiState.collectAsState()

    var openAlertDialog by remember { mutableStateOf(false) }
    if (openAlertDialog) {
        RemoveMedRecordAlert(
            navigateUp = { navController.navigateUp() },
            closeAlertDialog = { openAlertDialog = !openAlertDialog }
        )
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(R.string.therapy_title),
                canNavigateBack = true,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier
                    .padding(vertical = 50.dp)
            ) {
                ShowMedRecordData(medRecord)
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.pet_icon),
                        contentDescription = null,
                        contentScale = ContentScale.Inside,
                        modifier = Modifier
                            .size(100.dp)
                            .clip(CircleShape)
                            .background(LightBlueBackground)
                    )
                }

            }
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.Bottom,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                // Кнопка редактирования
                Button(
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .weight(1f),
                    contentPadding = PaddingValues(start = 1.dp, end = 1.dp),
                    border = BorderStroke(1.dp, GreenButton),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = GreenButton),
                    onClick = {
                        navController.navigate("${Routes.UpdateMedRecord.route}/$medRecordId") {
                            launchSingleTop = true
                        }
                    },
                ) {
                    Text(
                        text = stringResource(id = R.string.edit_button_description),
                        modifier = Modifier.padding(start = 5.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }

                Spacer(modifier = Modifier.width(24.dp))

                // кнопка удаления
                Button(
                    border = BorderStroke(1.dp, RedButton),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = RedButton),
                    modifier = Modifier
                        .padding(bottom = 40.dp)
                        .weight(1f),
                    onClick = {
                        openAlertDialog = true
                    },
                ) {
                    Text(
                        text = stringResource(id = R.string.therapy_delete_button),
                        modifier = Modifier.padding(start = 10.dp),
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

//TODO: suspend fun removeTherapy (profileId: String?, therapyId: String?)

