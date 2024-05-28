package com.f4.mypet.ui.screens.auth.login

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import com.f4.mypet.ui.screens.wall.PetsWallViewModel
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightGrayTint
import com.f4.mypet.util.UIState
import com.vk.id.AccessToken
import com.vk.id.OAuth
import com.vk.id.VKID
import com.vk.id.onetap.common.OneTapOAuth
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

            // email
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(stringResource(id = R.string.login_enter)) },
                placeholder = { Text(stringResource(id = R.string.login_placeholder)) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    IconButton(onClick = { email = "" }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
            )

            // password
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(stringResource(id = R.string.login_password_enter)) },
                placeholder = { Text(stringResource(id = R.string.login_password_enter)) },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    IconButton(onClick = { password = "" }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
            )

            // Кнопка "Войти"
            Button(
                onClick = {
                    scope.launch {
                        viewModel.login(email, password)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Text(
                    text = stringResource(id = R.string.login_button),
                    textAlign = TextAlign.Center,
                    color = Color.White
                )
            }

            // Кнопка "Зарегистрироваться"
            TextButton(
                onClick = {
                    navController.navigate(Routes.Register.route) {
                        popUpTo(Routes.ListProfile.route)
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.login_registration_button),
                    textAlign = TextAlign.Center,
                    color = LightGrayTint
                )
            }

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
