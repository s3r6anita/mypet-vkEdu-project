package com.f4.mypet.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.f4.mypet.R

const val SHOWSNACKDURATION = 3000L

@Composable
fun MyPetSnackBar(text: String) {
    Snackbar(
        modifier = Modifier
            .height(90.dp)
            .width(260.dp)
            .padding(bottom = 30.dp),
        shape = ShapeDefaults.Medium
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                Icons.Default.CheckCircle,
                contentDescription = stringResource(id = R.string.clear)
            )
            Spacer(modifier = Modifier.size(10.dp))
            Text(text = text)
        }
    }
}
