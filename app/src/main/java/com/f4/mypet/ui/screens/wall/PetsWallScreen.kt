package com.f4.mypet.ui.screens.wall

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.f4.mypet.ui.components.BottomBarData
import com.f4.mypet.ui.components.MyPetBottomBar
import com.f4.mypet.ui.screens.ErrorScreen
import com.vk.sdk.api.photos.dto.PhotosPhotoDto
import com.vk.sdk.api.photos.dto.PhotosPhotoSizesTypeDto
import com.vk.sdk.api.wall.dto.WallGetResponseDto
import com.vk.sdk.api.wall.dto.WallWallItemDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentTypeDto
import kotlinx.coroutines.launch

@Composable
fun PetsWallScreen(
    navController: NavHostController,
    canNavigateBack: Boolean,
    profileId: Int,
    viewModel: PetsWallViewModel = hiltViewModel()
) {
    val localScope = rememberCoroutineScope()
    val response by viewModel.responseUiState.collectAsState()

    LaunchedEffect(Unit) {
        localScope.launch {
            viewModel.getPosts()
        }
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
            .verticalScroll(rememberScrollState())
        if (response != null) {
            Wall(wallResponse = response!!, modifier = modifier)
        } else {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                ErrorScreen(retryAction = { })
            }
        }

    }



}


@Composable
fun Wall(wallResponse: WallGetResponseDto, modifier: Modifier) {
    Column(modifier = modifier,
        content = {
            val posts = wallResponse.items
            posts.forEach {
                val post = it as WallWallItemDto.WallWallpostFullDto
                Post(post = post)
            }
        })

}

@Composable
fun Post(post: WallWallItemDto.WallWallpostFullDto) {
    Row() {
        Card(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth()
        ) {
            Text(text = "Мой питомец")
            Text(text = post.text ?: "")
            if (post.attachments != null) {
                PhotosGridScreen(photos = post.attachments!!.filter { it.type == WallWallpostAttachmentTypeDto.PHOTO })
            }

            Spacer(modifier = Modifier.padding(vertical = 30.dp))
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PhotosGridScreen(photos: List<WallWallpostAttachmentDto>, modifier: Modifier = Modifier) {
    Row {
        photos.forEach {
            PhotoCard(photo = it.photo!!)
        }
    }

}

@Composable
fun PhotoCard(photo: PhotosPhotoDto, modifier: Modifier = Modifier) {
    Row {

        Card(
            modifier = modifier,
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            var retryHash by remember { mutableStateOf(0) }
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(photo.sizes?.find { it.type == PhotosPhotoSizesTypeDto.X }?.url)
                    .setParameter("retry_hash", retryHash, memoryCacheKey = null)
                    .crossfade(true)
                    .build(),
                loading = {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(12.dp)
                            .size(24.dp)
                    )
                },
                error = {
//                IconButton(
//                    onClick = { retryHash++ }
//                ) {
//                    Icon(
//                        painter = painterResource(id = R.drawable.ic_refresh),
//                        contentDescription = "refresh"
//                    )
//                }
                    Log.d("TAAAG", "ERROR")
                },
                contentDescription = null
            )
        }
    }
}
