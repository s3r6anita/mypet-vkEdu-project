package com.f4.mypet.ui.screens.profile.list

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.ui.screens.ErrorScreen
import com.f4.mypet.ui.screens.LoadingScreen
import com.f4.mypet.ui.screens.profile.list.success.SuccessListProfileScreen
import com.f4.mypet.util.UiState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun ListProfileScreen(
    snackbarHostState: SnackbarHostState,
    navController: NavHostController,
    globalScope: () -> CoroutineScope,
    viewModel: ListProfileViewModel = hiltViewModel()
) {
    val localScope = rememberCoroutineScope()
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        localScope.launch {
//            viewModel.getPetsProfiles()
            viewModel.getPetsProfilesFromNetwork()
        }
    }

    when (uiState) {
        UiState.Loading -> LoadingScreen()
        UiState.Success -> SuccessListProfileScreen(
            snackbarHostState = snackbarHostState,
            globalScope = globalScope(),
            navController = navController
        )
        else -> ErrorScreen(retryAction = {
            localScope.launch {
                viewModel.getPetsProfiles()
            }
        })
    }
}
