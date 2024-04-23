package com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.ui.theme.OutlinedTextFieldColor

@Composable
fun TherapyNotesField(
    isCreateScreen: Boolean,
    modifier: Modifier = Modifier,
    onNotesChange: (String) -> Unit
) {
    OutlinedTextField(
        value = "",
        onValueChange = onNotesChange,
        label = {
            if (isCreateScreen) Text(
                stringResource(R.string.cu_therapy_tmp_notice),
                style = TextStyle(color = OutlinedTextFieldColor)
            ) else {
                Text(stringResource(R.string.cu_therapy_tmp_notice)) //TODO подтягивание данных из БД
            }
        },
        modifier = modifier.height(120.dp),
        shape = RoundedCornerShape(12.dp),
    )
}
