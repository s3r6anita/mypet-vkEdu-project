package com.f4.mypet.ui.screens.wall.elements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vk.sdk.api.photos.dto.PhotosPhotoSizesTypeDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentDto

@Composable
fun FullScreenPhoto(attachment: WallWallpostAttachmentDto, onDismiss: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray.copy(alpha = 0.75f))
            .clickable { onDismiss() }
        ) {}
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(attachment.photo?.sizes?.find { it.type == PhotosPhotoSizesTypeDto.X }?.url)
                .crossfade(true)
                .build(),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
        )

    }
}