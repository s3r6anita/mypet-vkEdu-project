package com.f4.mypet.ui.screens.wall.elements

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.vk.sdk.api.wall.dto.WallGetResponseDto
import com.vk.sdk.api.wall.dto.WallWallItemDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentDto
import com.vk.sdk.api.wall.dto.WallWallpostAttachmentTypeDto
import kotlinx.collections.immutable.toImmutableList

@Composable
fun Wall(
    response: WallGetResponseDto,
    modifier: Modifier,
    vkPetsPhoto: String,
    openPhoto: (attachment: WallWallpostAttachmentDto) -> Unit,
) {
    LazyColumn(modifier = modifier,
        content = {
            val posts = response.items
            posts.forEach { wallItem ->
                val post = wallItem as WallWallItemDto.WallWallpostFullDto
                if (post.attachments!!.filter { attach ->
                        attach.type == WallWallpostAttachmentTypeDto.VIDEO
                    }.isEmpty()) {
                    item {
                        GroupTitle(vkPetsPhoto)
                    }
                    item {
                        Text(text = post.text ?: "")
                    }
                    val photos = post.attachments!!.filter { attach ->
                        attach.type == WallWallpostAttachmentTypeDto.PHOTO
                    }
                    if (photos.isNotEmpty()) {
                        item {
                            PhotoCarousel(photos.toImmutableList(), openPhoto)
                        }
                    }
                }
            }
        })
}
