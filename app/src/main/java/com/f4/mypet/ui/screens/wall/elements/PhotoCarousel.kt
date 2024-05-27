package com.f4.mypet.ui.screens.wall.elements

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vk.sdk.api.photos.dto.PhotosPhotoSizesTypeDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentDto

@Composable
fun PhotoCarousel(
    photos: List<WallWallpostAttachmentDto>,
    openPhoto: (attachment: WallWallpostAttachmentDto) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .padding(vertical = 10.dp)
    ) {
        items(photos) { attachment ->
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(attachment.photo?.sizes?.find { it.type == PhotosPhotoSizesTypeDto.X }?.url)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                modifier = Modifier
                    .clickable { openPhoto(attachment) }
                    .fillMaxWidth()
            )
        }
    }
}