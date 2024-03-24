package com.f4.mypet.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.f4.mypet.R
import com.f4.mypet.navigation.BottomBarRoutes
import com.f4.mypet.navigation.Routes

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyPetTopBar(
    text: String,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    actions: @Composable() (RowScope.() -> Unit),
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = text)
        },
        scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                    )
                }
            }
        },
        actions = actions,
        modifier = modifier
    )
}

@Composable
fun MyPetBottomBar(
    navController: NavHostController,
    profileId: Int?,
    canNavigateBack: Boolean?,
    items: List<BottomBarRoutes>,
    modifier: Modifier = Modifier
) {
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = backStackEntry?.destination?.route

    NavigationBar(
        modifier = modifier
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.title)
                    )
                },
                label = { Text(text = stringResource(item.title)) },
                selected = (currentScreen == item.route),
                onClick = {
                    navController.navigate(item.route + "/" + profileId + "/" + canNavigateBack) {
                        popUpTo(Routes.ListProfile.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}

@Composable
fun CustomSnackBar(text: String) {
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

@Composable
fun ClearIcon(clear: () -> Unit) {
    IconButton(onClick = clear) {
        Icon(
            Icons.Default.Clear,
            contentDescription = stringResource(id = R.string.clear)
        )
    }
}
