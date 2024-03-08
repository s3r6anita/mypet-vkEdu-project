package com.f4.mypet.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.f4.mypet.R
import java.text.SimpleDateFormat

// Формат даты и времени
@SuppressLint("SimpleDateFormat")
val dateFormat = SimpleDateFormat("dd.MM.yyyy")
@SuppressLint("SimpleDateFormat")
val timeFormat = SimpleDateFormat("HH:mm")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPetTopBar(
    text: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    actions: @Composable() (RowScope.() -> Unit),
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = text)
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                    )
                }
            }
        },
        actions = actions,
        modifier = modifier
    )
}