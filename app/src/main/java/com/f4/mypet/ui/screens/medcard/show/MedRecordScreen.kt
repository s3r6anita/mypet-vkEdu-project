package com.f4.mypet.ui.screens.medcard.show

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
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
import com.f4.mypet.ui.components.ButtonComponent
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.components.StatusDialog
import com.f4.mypet.ui.screens.LoadingScreen
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

    val medRecord by viewModel.medRecordUiState.collectAsState()
    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getMedRecord(medRecordId)
        }
    }

    // для удаления
    val msg by viewModel.msg.collectAsState()
    var showStatusDialog by remember { mutableStateOf(false) }
    var openAlertDialog by remember { mutableStateOf(false) }

    LaunchedEffect(msg) {
        if (msg != null && msg != "") {
            showStatusDialog = true
        }
        if (msg == null) {
            navController.navigateUp()
        }
    }

    if (openAlertDialog) {
        RemoveMedRecordAlert(
            medRecord = medRecord,
            closeAlertDialog = { openAlertDialog = !openAlertDialog },
        )
    }
    if (showStatusDialog) {
        StatusDialog(msg) {
            showStatusDialog = !showStatusDialog
            viewModel.resetMsg()
        }
    }


    if (medRecord.id == -1) {
        LoadingScreen()
    } else {
        Scaffold(
            topBar = {
                MyPetTopBar(
                    text = stringResource(R.string.medrecord_show_title),
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
                    .padding(20.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Box {
                    ShowMedRecordData(medRecord)
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.therapy_icon),
                            contentDescription = null,
                            contentScale = ContentScale.Inside,
                            modifier = Modifier
                                .size(100.dp)
                                .clip(CircleShape)
                                .background(LightBlueBackground)
                        )
                    }
                }
                Column {
                    // кнопка редактирования
                    ButtonComponent(
                        onClick = {
                            navController.navigate("${Routes.UpdateMedRecord.route}/$medRecordId") {
                                launchSingleTop = true
                            }
                        },
                        text = stringResource(id = R.string.edit_button_description),
                        color = ButtonDefaults.outlinedButtonColors(contentColor = GreenButton),
                        icon = Icons.Default.Edit,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textColor = GreenButton,
                        borderColor = GreenButton,
                        enabled = true
                    )
                    // кнопка удаления
                    ButtonComponent(
                        onClick = {
                            openAlertDialog = true
                        },
                        text = stringResource(id = R.string.procedure_screen_delete),
                        color = ButtonDefaults.outlinedButtonColors(contentColor = RedButton),
                        icon = Icons.Default.Delete,
                        modifier = Modifier
                            .fillMaxWidth(),
                        textColor = RedButton,
                        borderColor = RedButton,
                        enabled = true
                    )
                }
            }
        }
    }
}
