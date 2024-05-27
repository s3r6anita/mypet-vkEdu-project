package com.f4.mypet.ui.screens.wall

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.screens.LoadingScreen
import com.f4.mypet.ui.screens.wall.elements.FullScreenPhoto
import com.f4.mypet.ui.screens.wall.elements.Wall
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentDto
import kotlinx.coroutines.launch

@Composable
fun PetsWallScreen(
    navController: NavHostController,
    canNavigateBack: Boolean,
    profileId: Int,
    viewModel: PetsWallViewModel = hiltViewModel()
) {
    val localScope = rememberCoroutineScope()
    val wallResponse by viewModel.responseUiState.collectAsState()
    val vkPetsPhoto by viewModel.vkpetsUIState.collectAsState()

    LaunchedEffect(Unit) {
        localScope.launch {
            viewModel.getPosts()
        }
    }

    var selectedPhoto by remember {
        mutableStateOf<WallWallpostAttachmentDto?>(null)
    }

    Scaffold(
        bottomBar = {
            MyPetBottomBar(
                profileId = profileId,
                canNavigateBack = canNavigateBack,
                items = BottomBarData.items,
                getNavController = { navController }
            )
        }
    ) { innerPadding ->
        val modifier = Modifier
            .fillMaxWidth()
            .padding(innerPadding)
            .padding(horizontal = 20.dp)
        if (wallResponse != null && vkPetsPhoto != null) {
            Wall(
                response = wallResponse!!, modifier = modifier,
                vkPetsPhoto = vkPetsPhoto?.photo50!!
            ) { attachment -> selectedPhoto = attachment }
            if (selectedPhoto != null) {
                FullScreenPhoto(attachment = selectedPhoto!!) {
                    selectedPhoto = null
                }
            }
        } else {
            LoadingScreen()
        }
    }
}
