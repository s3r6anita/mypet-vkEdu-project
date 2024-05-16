package com.f4.mypet.ui.screens.medcard.createUpdate.screenComponents

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.ui.theme.OutlinedTextFieldColor

@Composable
fun TherapyNameField(
    isCreateScreen: Boolean,
    onNameChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    givenTitle: String
) {
    var title by remember { mutableStateOf(givenTitle) }
    title = givenTitle

    OutlinedTextField(
        modifier = modifier.padding(bottom = 10.dp),
        value = title,
        onValueChange = { title = it; onNameChange(it) },
        label = {
            if (isCreateScreen) Text(
                stringResource(R.string.cu_therapy_name),
                style = TextStyle(color = OutlinedTextFieldColor)
            ) else {
                Text(stringResource(R.string.cu_therapy_name)) //TODO подтягивание данных из БД
            }
        },
        shape = RoundedCornerShape(12.dp)
    )
}
