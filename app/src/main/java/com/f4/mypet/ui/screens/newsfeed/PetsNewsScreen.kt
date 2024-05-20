package com.f4.mypet.ui.screens.newsfeed

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.ui.screens.procedure.list.ListProcedureViewModel

@Composable
fun PetsNewsScreen(
    navController: NavHostController,
    canNavigateBack: Boolean,
    viewModel: ListProcedureViewModel = hiltViewModel()
) {

}
