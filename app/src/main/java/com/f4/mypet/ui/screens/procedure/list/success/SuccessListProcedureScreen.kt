package com.f4.mypet.ui.screens.procedure.list.success

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.f4.mypet.ui.screens.procedure.list.ListProcedureViewModel
import com.f4.mypet.ui.screens.procedure.list.ProcedureItem
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightGreenBackground
import kotlinx.coroutines.cancelChildren

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SuccessListProcedureScreen(
    canNavigateBack: Boolean,
    profileId: Int,
    getNavController: () -> NavHostController,
    viewModel: ListProcedureViewModel = hiltViewModel()
) {
    val navController = getNavController()

    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val pullRefreshState =
        rememberPullRefreshState(isRefreshing, { viewModel.refreshProcedures(profileId) })


    val procedures by viewModel.proceduresUiState.collectAsState()
    val pet = viewModel.pet
    val titles by viewModel.titlesUiState.collectAsState()

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(id = R.string.list_procedure_screen_title),
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
        },
    ) { innerPadding ->

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top,
            modifier = Modifier
                .pullRefresh(pullRefreshState)
                .fillMaxSize()
                .padding(innerPadding)
                .padding(20.dp),
        ) {
            PetCardHeader(petName = pet.name, backgroundColor = LightGreenBackground)

            PullRefreshIndicator(
                isRefreshing,
                pullRefreshState,
                Modifier.align(Alignment.CenterHorizontally)
            )

            // список процедур
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                procedures.forEach { procedure ->
                    ProcedureItem(
                        procedure = procedure,
                        navController = navController,
                        title = titles.find { title -> title.id == procedure.title }?.name
                            ?: stringResource(id = R.string.unknown)
                    )
                }

                // кнопка ADD
                ButtonComponent(
                    onClick = {
                        navController.navigate("${Routes.CreateProcedure.route}/$profileId") {
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
