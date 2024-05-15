package com.f4.mypet.ui.screens.procedure.createUpdate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.ui.screens.ErrorScreen
import com.f4.mypet.ui.screens.LoadingScreen
import com.f4.mypet.ui.screens.procedure.createUpdate.success.SuccessCUProcedureScreen
import kotlinx.coroutines.launch

@Composable
fun CreateUpdateProcedureScreen(
    navController: NavHostController,
    isCreateScreen: Boolean,
    profileId: Int,
    procedureId: Int = -1,
    viewModel: CreateUpdateProcedureViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPetProcedure(procedureId)
        }
    }
    when (uiState) {
        UiState.Loading -> LoadingScreen()
        UiState.Success -> SuccessCUProcedureScreen(
            navController = navController,
            isCreateScreen = isCreateScreen,
            profileId = profileId,
        )
        else -> ErrorScreen(retryAction = viewModel::getPetProcedure, procedureId)
    }
}
