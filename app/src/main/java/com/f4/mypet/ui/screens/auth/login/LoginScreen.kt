package com.f4.mypet.ui.screens.auth.login

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
import androidx.compose.ui.platform.LocalContext
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
import com.f4.mypet.ui.screens.wall.PetsWallViewModel
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.util.UIState
import com.vk.id.VKID
import com.vk.id.onetap.compose.onetap.OneTap
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    navController: NavHostController,
    viewModel: LoginViewModel = hiltViewModel(),
    viewModelPets: PetsWallViewModel = hiltViewModel(),
) {
    val localContext = LocalContext.current
    val scope = rememberCoroutineScope()
    val msg by viewModel.msg.collectAsState()
    val uiState by viewModel.uiState.collectAsState()

    var openErrorAlert by remember {
        mutableStateOf(false)
    }
    var email by remember {
        mutableStateOf("admin@admin.com")
    }
    var password by remember {
        mutableStateOf("1qazxsw2")
    }


    LaunchedEffect(uiState) {
        if (uiState == UIState.Success) {
            if (msg == null) {
                navController.navigate(Routes.ListProfile.route) {
                    popUpTo(START)
                    restoreState = true
                    launchSingleTop = true
                }
            }
        }
        if (uiState == UIState.Error) {
            openErrorAlert = true
        }
    }

    if (openErrorAlert) {
        LoginErrorAlert(
            msg = msg,
            closeAlert = { openErrorAlert = !openErrorAlert },
            getNavController = { navController },
            retryAction = {
                scope.launch {
                    viewModel.login(email, password)
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

            // email
            OutlinedTextFieldComponent(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(id = R.string.login_enter)) },
                placeholder = { Text(stringResource(id = R.string.login_placeholder)) },
                onIconClick = { email = "" },
                icon = Icons.Default.Clear,
                modifier = Modifier,
                isError = false,
                supportingText = null,
            )

            // password
            PasswordFieldComponent(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.login_password_enter)) },
                placeholder = { Text(stringResource(id = R.string.login_password_enter)) },
                onIconClick = { password = "" },
                icon = Icons.Default.Clear,
                modifier = Modifier,
                isError = false,
            )

            // Кнопка "Войти"
            ButtonComponent(
                onClick = {
                    scope.launch {
                        viewModel.login(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.login_button),
                color = ButtonDefaults.buttonColors(containerColor = GreenButton),
                textColor = Color.White,
                borderColor = GreenButton,
                icon = null,
                enabled = true,
            )

            // Кнопка "Зарегистрироваться"
            TextButtonComponent(
                onClick = {
                    navController.navigate(Routes.Register.route) {
                        popUpTo(Routes.ListProfile.route)
                        launchSingleTop = true
                    }
                },
                text = stringResource(id = R.string.login_registration_button)
            )

            // кнопка VK ID
            OneTap(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp),
                vkid = VKID(localContext),
                signInAnotherAccountButtonEnabled = true,
                onFail = viewModel.getOneTapFailCallback(localContext),
                onAuth = viewModel.getOneTapSuccessCallback { token ->
                    scope.launch {
                        viewModel.saveToken(token)
                        viewModel.loginByVK(token)
                    }
                }
            )
        }
    }
}
