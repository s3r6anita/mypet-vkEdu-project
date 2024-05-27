package com.f4.mypet.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.ui.screens.procedure.createUpdate.success.getOutLinedTextFieldColors

@Suppress("LongParameterList")
@Composable
fun PasswordFieldComponent(
    value: String,
    onValueChange: (String) -> Unit,
    label: @Composable (() -> Unit)?,
    placeholder: @Composable (() -> Unit)?,
    modifier: Modifier,
    icon: ImageVector,
    onIconClick: () -> Unit,
    isError: Boolean,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = label,
        placeholder = placeholder,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        trailingIcon = {
            IconButton(onClick = onIconClick) {
                Icon(
                    icon,
                    contentDescription = stringResource(id = R.string.clear)
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        isError = isError,
        colors = getOutLinedTextFieldColors()
    )
}
