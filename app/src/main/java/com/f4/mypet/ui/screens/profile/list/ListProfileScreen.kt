package com.f4.mypet.ui.screens.profile.list

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.edit
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavOptionsBuilder
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.MyPetSnackBar
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.theme.BlueCheckbox
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.ui.theme.LightGrayTint
import com.f4.mypet.ui.theme.White
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

@Composable
fun ListProfileScreen(
    snackbarHostState: SnackbarHostState,
    globalScope: CoroutineScope,
    navigate: (String, NavOptionsBuilder.() -> Unit) -> Unit,
    viewModel: ListProfileViewModel = hiltViewModel()
) {
    val localScope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        localScope.launch {
            viewModel.getPetsProfiles()
        }
    }
    val pets by viewModel.petsUiState.collectAsState()

    val preferences = LocalContext.current.getSharedPreferences("pref", Context.MODE_PRIVATE)
    val value = preferences.getBoolean("rememberUserChoice", true)
    val (rememberUserChoice, onStateChange) = remember { mutableStateOf(value) }

    preferences.edit {
        putBoolean("rememberUserChoice", rememberUserChoice)
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(id = Routes.ListProfile.title),
                canNavigateBack = false,
                navigateUp = { },
                actions = {
                    // TODO: кнопка обратной связи
                    // кнопка входа
                    IconButton(onClick = {
                        navigate(Routes.Login.route) {
                            launchSingleTop = true
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = stringResource(id = R.string.login_button)
                        )
                    }
                }
            )
        },
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState
            ) {
                MyPetSnackBar(it.visuals.message)
            }
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(650.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
//              чек бокс "Запомнить мой выбор"
                val (rememberUserChoice, onStateChange) = remember { mutableStateOf(false) }
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 30.dp)
                        .toggleable(
                            value = rememberUserChoice,
                            onValueChange = { onStateChange(!rememberUserChoice) },
                            role = Role.Checkbox
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Checkbox(
                        checked = rememberUserChoice,
                        onCheckedChange = null,
                        modifier = Modifier.padding(15.dp),
                        colors = CheckboxDefaults.colors(
                            checkedColor = BlueCheckbox,
                            uncheckedColor = LightGrayTint,
                            checkmarkColor = White
                        )
                    )
                    Text(
                        text = stringResource(R.string.remember_my_choice),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }

//            список питомцев
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    pets.forEach { pet ->
                        PetItem(
                            pet = pet,
                            canNavigateBack = !rememberUserChoice,
                            navigate = navigate,
                            closeSnackbar = { globalScope.coroutineContext.cancelChildren() }
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                    }
                }
            }

//            кнопка добавления нового питомца в список
            Button(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    globalScope.coroutineContext.cancelChildren()
                    navigate(Routes.CreateProfile.route) { launchSingleTop = true }
                },
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(id = R.string.list_profile_screen_add_button_icon_description)
                )
                Text(
                    text = stringResource(id = R.string.add_button_description),
                    Modifier.padding(start = 10.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
