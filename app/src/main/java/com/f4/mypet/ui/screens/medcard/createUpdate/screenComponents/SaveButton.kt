package com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents

import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.f4.mypet.R
import com.f4.mypet.ui.components.ButtonComponent
import com.f4.mypet.ui.theme.GreenButton

@Composable
fun SaveButton() {
    ButtonComponent(
        onClick = {
            // TODO: переход
        },
        text = stringResource(id = R.string.save_button_description),
        color = ButtonDefaults.buttonColors(containerColor = GreenButton),
        icon = null,
        modifier = Modifier,
        textColor = Color.White,
        borderColor = GreenButton,
        enabled = true,
    )
}
