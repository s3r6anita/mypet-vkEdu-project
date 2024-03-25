package com.f4.mypet.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPetTopBar(
    text: String,
    canNavigateBack: Boolean = false,
    navigateUp: () -> Unit,
    actions: @Composable() RowScope.() -> Unit = { },
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
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                    )
                }
            }
        },
        actions = actions,
        modifier = modifier
    )
}
