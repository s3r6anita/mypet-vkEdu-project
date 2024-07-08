package com.f4.mypet.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.f4.mypet.R

@Suppress("LongParameterList")
@Composable
fun ButtonComponent(
    onClick: () -> Unit,
    text: String,
    color: ButtonColors,
    textColor: Color,
    borderColor: Color,
    modifier: Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(vertical = 10.dp),
        shape = RoundedCornerShape(8.dp),
        colors = color,
        border = BorderStroke(1.dp, borderColor),
        enabled = enabled,
    ) {
        if (icon != null){
            Icon(
                imageVector = icon,
                contentDescription = stringResource(id = R.string.icon_description),
                tint = textColor,
            )
        }
        Text(
            text = text,
            textAlign = TextAlign.Center,
            color = textColor,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(vertical = 2.dp, horizontal = 12.dp),
        )
    }
}
