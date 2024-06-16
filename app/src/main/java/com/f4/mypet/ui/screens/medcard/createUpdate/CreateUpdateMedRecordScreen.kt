package com.f4.mypet.ui.screens.medcard.createUpdate


import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.ui.components.StatusDialog
import com.f4.mypet.ui.screens.ErrorScreen
import com.f4.mypet.ui.screens.LoadingScreen
import com.f4.mypet.ui.screens.medcard.createUpdate.success.SuccessCUMedCardScreen
import com.f4.mypet.util.UIState
import kotlinx.coroutines.launch

@Suppress("LongParameterList")
@Composable
fun CreateUpdateMedRecordScreen(
    navController: NavHostController,
    snackbarHostState: SnackbarHostState,
    isCreateScreen: Boolean,
    profileId: Int = -1,
    medRecordId: Int = -1,
    viewModel: CreateUpdateMedRecordViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPetMedRecord(medRecordId)
        }
    }

    val msg by viewModel.msg.collectAsState()
    var showStatusDialog by remember { mutableStateOf(false) }

    LaunchedEffect(msg) {
        if (msg != null && msg != "") {
            showStatusDialog = true
        }
        if (msg == null) {
            navController.navigateUp()
        }
    }
    if (showStatusDialog) {
        StatusDialog(msg) {
            showStatusDialog = !showStatusDialog
            viewModel.resetMsg()
        }
    }

    when (uiState) {
        UIState.Loading -> LoadingScreen()
        UIState.Success -> SuccessCUMedCardScreen(
            isCreateScreen = isCreateScreen,
            navController = navController,
            snackbarHostState = snackbarHostState,
            profileId = profileId
        )

        else -> ErrorScreen(retryAction = {
            scope.launch {
                viewModel.getPetMedRecord(medRecordId)
            }
        })
    }

}
