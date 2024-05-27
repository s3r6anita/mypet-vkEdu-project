package com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import com.f4.mypet.ui.theme.BlueCheckbox
import com.f4.mypet.ui.theme.LightBlueBackground
import com.f4.mypet.ui.theme.LightGrayTint
import com.f4.mypet.ui.theme.RedButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getDropdownMenuColors(): TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(
    focusedBorderColor = LightBlueBackground,
    unfocusedBorderColor = LightGrayTint,
    containerColor = MaterialTheme.colorScheme.onSecondary,
    errorBorderColor = RedButton
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun getOutLinedTextFieldColors(): TextFieldColors = ExposedDropdownMenuDefaults.textFieldColors(
    unfocusedIndicatorColor = LightBlueBackground,
    focusedIndicatorColor = BlueCheckbox,
    unfocusedContainerColor = MaterialTheme.colorScheme.onSecondary,
    focusedContainerColor = LightBlueBackground
)
