package com.f4.mypet.ui.screens.auth.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.Purple40
import com.f4.mypet.util.UIState
import com.f4.mypet.util.validate
import com.f4.mypet.util.validateEmail
import com.f4.mypet.util.validatePassword
import kotlinx.coroutines.launch

@Composable
fun RegistrationScreen(
    navController: NavHostController,
    viewModel: RegistrationViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val msg by viewModel.msg.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var openErrorAlert by remember {
        mutableStateOf(false)
    }
    val isCorrectData by remember {
        mutableStateOf(mutableListOf(false, false, false, false, false))
    }
    var email by remember {
        mutableStateOf("")
    }
    var password1 by remember {
        mutableStateOf("")
    }
    var password2 by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }

    LaunchedEffect(uiState) {
        if (uiState == UIState.Success) {
            if (msg == null) {
                navController.navigate(Routes.ListProfile.route) {
                    popUpTo(START)
                    restoreState = true
                }
            }
        }
        if (uiState == UIState.Error) {
            openErrorAlert = true
        }
    }

    if (openErrorAlert) {
        RegistrationErrorAlert(
            msg = msg,
            closeAlert = { openErrorAlert = !openErrorAlert },
            retryAction = {
                scope.launch {
                    viewModel.register(email, password1, name)
                }
            }
        )
    }

    Scaffold { innerPadding ->
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
                    .padding(vertical = 15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.login_title),
                    color = Color.Black,
                    style = MaterialTheme.typography.displayMedium
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.reg_title),
                    color = Purple40,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // name
            OutlinedTextField(
                value = name,
                onValueChange = {
                    name = it
                    isCorrectData[0] = validate(name)
                },
                label = { Text(stringResource(id = R.string.reg_name_label)) },
                trailingIcon = {
                    IconButton(onClick = {
                        name = ""
                        isCorrectData[0] = validate(name)
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = !validate(name)
            )

            // email
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    isCorrectData[1] = validate(name)
                },
                label = { Text(stringResource(id = R.string.login_enter)) },
                placeholder = { Text(stringResource(id = R.string.login_placeholder)) },
                trailingIcon = {
                    IconButton(onClick = {
                        email = ""
                        isCorrectData[1] = validate(name)
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = !validateEmail(email),
            )

            // password
            OutlinedTextField(
                value = password1,
                onValueChange = {
                    password1 = it
                    isCorrectData[2] = validate(name)
                    isCorrectData[4] = password1 == password2
                },
                label = { Text(stringResource(id = R.string.login_password_enter)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = {
                        password1 = ""
                        isCorrectData[2] = validate(name)
                        isCorrectData[4] = password1 == password2
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = !validatePassword(password1)
            )

            // password2
            OutlinedTextField(
                value = password2,
                onValueChange = {
                    password2 = it
                    isCorrectData[3] = validate(name)
                    isCorrectData[4] = password1 == password2
                },
                label = { Text(stringResource(id = R.string.login_password_confirm)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                trailingIcon = {
                    IconButton(onClick = {
                        password2 = ""
                        isCorrectData[3] = validate(name)
                        isCorrectData[4] = password1 == password2
                    }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                isError = password1 != password2
            )

            // Кнопка "Зарегистрироваться"
            Button(
                onClick = {
                    if (password1 == password2) {
                        scope.launch {
                            viewModel.register(email, password1, name)
                        }
                    } else {
                        openErrorAlert = true
                    }
                },
                enabled = isCorrectData.all { it },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Text(
                    text = stringResource(id = R.string.login_registration_button),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }
        }
    }
}
