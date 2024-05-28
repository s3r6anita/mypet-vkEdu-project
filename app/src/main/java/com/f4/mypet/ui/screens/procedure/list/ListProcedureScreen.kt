package com.f4.mypet.ui.screens.procedure.list

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
import com.f4.mypet.ui.screens.LoadingScreen
import com.f4.mypet.ui.screens.procedure.list.success.SuccessListProcedureScreen
import kotlinx.coroutines.launch

@Composable
fun ListProcedureScreen(
    navController: NavHostController,
    profileId: Int,
    canNavigateBack: Boolean,
    viewModel: ListProcedureViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val msg by viewModel.msg.collectAsState()
    val pet = viewModel.pet

    LaunchedEffect(Unit) {
        scope.launch {
            viewModel.getTitles()
            viewModel.getPetProcedures(profileId)
        }
    }

    var showStatusDialog by remember { mutableStateOf(false) }

    if (msg != "") {
        showStatusDialog = true
    }
    if (showStatusDialog) {
        StatusDialog(msg) {
            showStatusDialog = !showStatusDialog
            viewModel.resetMsg()
        }
    }

    if (pet.id != -1) {
        SuccessListProcedureScreen(
            canNavigateBack,
            profileId,
            { navController }
        )
    } else {
        LoadingScreen()
    }
}
