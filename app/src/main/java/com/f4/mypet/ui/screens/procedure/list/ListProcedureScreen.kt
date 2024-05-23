package com.f4.mypet.ui.screens.procedure.list

import android.icu.text.CaseMap.Title
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.data.db.entities.Procedure
import com.f4.mypet.ui.screens.ErrorScreen
import com.f4.mypet.ui.screens.LoadingScreen
import com.f4.mypet.ui.screens.procedure.list.success.SuccessListProcedureScreen
import com.f4.mypet.util.UIState
import kotlinx.coroutines.launch

@Composable
fun ListProcedureScreen(
    navController: NavHostController,
    profileId: Int,
    canNavigateBack: Boolean,
    viewModel: ListProcedureViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    val procedures by viewModel.proceduresUiState.collectAsState()
    val titles by viewModel.titlesUiState.collectAsState()

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getPetProcedures(profileId)
        }
        scope.launch {
            viewModel.getTitles()
        }
    }

    if (procedures == emptyList<Procedure>() && titles == emptyList<Title>()) {
        LoadingScreen()
    } else {
        when (uiState) {
            UIState.Loading -> LoadingScreen()
            UIState.Success -> SuccessListProcedureScreen(
                canNavigateBack,
                profileId,
                { navController }
            )
            else -> ErrorScreen(retryAction = {
                scope.launch {
                    viewModel.refreshProcedures(profileId)
                }
            })
        }
    }
}
