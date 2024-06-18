package com.f4.mypet.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.navigation.START
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

sealed class BottomNavigationItems(
    val route: Routes.BottomBarRoutes,
    @DrawableRes val icon: Int,
    val hasNews: Boolean = false
){
    data object Procedures : BottomNavigationItems(
        route = Routes.BottomBarRoutes.ListProcedures,
        icon = R.drawable.procedures_icon
    )

    data object MedCard : BottomNavigationItems(
        route = Routes.BottomBarRoutes.ListMedRecords,
        icon = R.drawable.therapy_icon
    )

    data object Profile : BottomNavigationItems(
        route = Routes.BottomBarRoutes.Profile,
        icon = R.drawable.pet_icon
    )

    data object Wall : BottomNavigationItems(
        route = Routes.BottomBarRoutes.PetsWall,
        icon = R.drawable.newspaper_icon
    )
}

data object BottomBarData {
    var selectedItemIndex = 0
    val items = persistentListOf(
        BottomNavigationItems.Procedures,
        BottomNavigationItems.MedCard,
        BottomNavigationItems.Wall,
        BottomNavigationItems.Profile
    )
}

@Composable
fun MyPetBottomBar(
    profileId: Int,
    canNavigateBack: Boolean,
    items: ImmutableList<BottomNavigationItems>,
    getNavController: () -> NavHostController,
    modifier: Modifier = Modifier
) {
    val navController = getNavController()

    NavigationBar(
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                label = { Text(text = stringResource(item.route.title)) },
                selected = BottomBarData.selectedItemIndex == index,
                onClick = {
                    if (BottomBarData.selectedItemIndex != index) {
                        navController.navigate("${item.route.route}/$profileId/$canNavigateBack") {
                            popUpTo(if (canNavigateBack) Routes.ListProfile.route else START) {
                                inclusive = false
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                        BottomBarData.selectedItemIndex = index
                    }
                },
                icon = {
                    BadgedBox(
                        badge = {
                            if (item.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            painter = painterResource(item.icon),
                            contentDescription = stringResource(item.route.title)
                        )
                    }
                }
            )
        }
    }
}
