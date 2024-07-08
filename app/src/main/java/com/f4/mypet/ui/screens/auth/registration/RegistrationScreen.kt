package com.f4.mypet.ui.screens.auth.registration

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.ui.components.ButtonComponent
import com.f4.mypet.ui.components.OutlinedTextFieldComponent
import com.f4.mypet.ui.components.PasswordFieldComponent
import com.f4.mypet.ui.components.TextButtonComponent
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.Transparent
import com.f4.mypet.util.UIState
import com.f4.mypet.util.validate
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
                .padding(20.dp)
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
                    color = MaterialTheme.colorScheme.onBackground,
                    style = MaterialTheme.typography.displayMedium
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
            ) {
                Text(
                    text = stringResource(id = R.string.reg_title),
                    color = GreenButton,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            // name
            OutlinedTextFieldComponent(
                value = name,
                onValueChange = {
                    name = it
                    isCorrectData[0] = validate(name)
                },
                label = { Text(stringResource(id = R.string.reg_name_label)) },
                placeholder = { Text(stringResource(id = R.string.reg_name_label)) },
                onIconClick = {
                    name = ""
                    isCorrectData[0] = validate(name)
                },
                icon = Icons.Default.Clear,
                modifier = Modifier,
                isError = !validate(name),
                supportingText = null,
            )

            // email
            OutlinedTextFieldComponent(
                value = email,
                onValueChange = {
                    email = it
                    isCorrectData[1] = validate(name)
                },
                label = { Text(stringResource(id = R.string.login_enter)) },
                placeholder = { Text(stringResource(id = R.string.login_placeholder)) },
                onIconClick = {
                    email = ""
                    isCorrectData[1] = validate(name)
                },
                icon = Icons.Default.Clear,
                modifier = Modifier,
                isError = !validate(email),
                supportingText = null,
            )

            // password
            PasswordFieldComponent(
                value = password1,
                onValueChange = {
                    password1 = it
                    isCorrectData[2] = validate(name)
                    isCorrectData[4] = password1 == password2
                },
                label = { Text(stringResource(id = R.string.login_password_enter)) },
                placeholder = { Text(stringResource(id = R.string.login_password_enter)) },
                onIconClick = {
                    password1 = ""
                    isCorrectData[2] = validate(name)
                    isCorrectData[4] = password1 == password2
                },
                icon = Icons.Default.Clear,
                modifier = Modifier,
                isError = !validatePassword(password1),
            )
            // password2
            PasswordFieldComponent(
                value = password2,
                onValueChange = {
                    password2 = it
                    isCorrectData[3] = validate(name)
                    isCorrectData[4] = password1 == password2
                },
                label = { Text(stringResource(id = R.string.login_password_confirm)) },
                placeholder = { Text(stringResource(id = R.string.login_password_confirm)) },
                onIconClick = {
                    password2 = ""
                    isCorrectData[3] = validate(name)
                    isCorrectData[4] = password1 == password2
                },
                icon = Icons.Default.Clear,
                modifier = Modifier,
                isError = password1 != password2,
            )

            // Кнопка "Зарегистрироваться"
            ButtonComponent(
                onClick = {
                    if (password1 == password2) {
                        scope.launch {
                            viewModel.register(email, password1, name)
                        }
                    } else {
                        openErrorAlert = true
                    }
                },
                text = stringResource(id = R.string.login_registration_button),
                color = ButtonDefaults.buttonColors(containerColor = GreenButton),
                textColor = Color.White,
                borderColor = Transparent,
                icon = null,
                modifier = Modifier
                    .fillMaxWidth(),
                enabled = isCorrectData.all { it },
            )

            // Кнопка Назад
            TextButtonComponent(
                onClick = {
                    navController.navigate(START) {
                        popUpTo(START)
                        launchSingleTop = true
                    }
                },
                text = stringResource(id = R.string.back_button)
            )
        }
    }
}
