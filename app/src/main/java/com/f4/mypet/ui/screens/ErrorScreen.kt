package com.f4.mypet.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ThemedIconButton(
            imageVector = Icons.Default.Refresh,
            onClick = retryAction
        )
    }
}

@Composable
fun ThemedIconButton(
    imageVector: ImageVector,
    onClick: () -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.background
    val iconColor = if (backgroundColor.luminance() > 0.5f) {
        Color.Black
    } else {
        Color.White
    }

    IconButton(
        modifier = Modifier.size(64.dp),
        onClick = onClick
    ) {
        Icon(
            modifier = Modifier.size(64.dp),
            imageVector = imageVector,
            contentDescription = null,
            tint = iconColor
        )
    }
}
