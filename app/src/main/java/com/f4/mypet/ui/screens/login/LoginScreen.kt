package com.f4.mypet.ui.screens.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.f4.mypet.R
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightGrayTint

@Composable
fun HelloScreen(){
    Scaffold(
        topBar = {
            MyPetTopBar(
                text = "",
                canNavigateBack = false,
                navigateUp = {  },
                actions = {}
            )
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login_title),
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.displayMedium
                )
            }

            // email
            OutlinedTextField(
                value = "",
                onValueChange = {  },
                label = { Text(stringResource(id = R.string.login_enter)) },
                placeholder = { Text(stringResource(id = R.string.login_placeholder)) },
                trailingIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                singleLine = false,
                shape = RoundedCornerShape(12.dp)
            )

            // password
            OutlinedTextField(
                value = "",
                onValueChange = {  },
                label = { Text(stringResource(id = R.string.login_password_enter)) },
                placeholder = { Text(stringResource(id = R.string.login_password_enter)) },
                trailingIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                singleLine = false,
                shape = RoundedCornerShape(12.dp)
            )
            Button(
                onClick = {  },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            TextButton(
                onClick = {  },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.login_registration_button),
                    textAlign = TextAlign.Center,
                    color = LightGrayTint
                )
            }
        }
    }
}
