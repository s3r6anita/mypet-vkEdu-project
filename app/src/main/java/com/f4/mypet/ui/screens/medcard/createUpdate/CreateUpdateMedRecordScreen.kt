package com.f4.mypet.ui.screens.medcard.createUpdate


import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.ui.screens.ErrorScreen
import com.f4.mypet.ui.screens.LoadingScreen
import com.f4.mypet.ui.screens.medcard.createUpdate.success.SuccessCUMedCardScreen
import com.f4.mypet.util.UIState
import kotlinx.coroutines.launch


@Composable
fun CreateUpdateMedRecordScreen(
    navController: NavHostController,
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
    when (uiState) {
        UIState.Loading -> LoadingScreen()
        UIState.Success -> SuccessCUMedCardScreen(
            isCreateScreen = isCreateScreen,
            navController = navController,
            profileId = profileId
        )

        else -> ErrorScreen(retryAction = {
            scope.launch {
                viewModel.getPetMedRecord(medRecordId)
            }
        })
    }

}
