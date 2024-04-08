package com.f4.mypet.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlin.reflect.KFunction1

@Composable
fun ErrorScreen(retryAction: KFunction1<Int, Unit>, procedureId: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            modifier = Modifier.size(64.dp),
            onClick = { retryAction(procedureId) }
        ) {
            Icon(
                modifier = Modifier.size(64.dp),
                imageVector = Icons.Default.Refresh,
                contentDescription = null
            )
        }
    }
}