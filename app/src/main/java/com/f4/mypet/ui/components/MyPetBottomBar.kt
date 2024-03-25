package com.f4.mypet.ui.components

import android.util.Log
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
import com.f4.mypet.navigation.BottomBarRoutes
import com.f4.mypet.navigation.Routes

data class BottomNavigationItem(
    val bbroute: BottomBarRoutes,
    val hasNews: Boolean
)

object BottomBarData {
    val items = listOf(
        BottomNavigationItem(
            bbroute = BottomBarRoutes.ListProcedures,
            hasNews = false
        ),
        BottomNavigationItem(
            bbroute = BottomBarRoutes.MedCard,
            hasNews = false
        ),
        BottomNavigationItem(
            bbroute = BottomBarRoutes.Profile,
            hasNews = false
        )
    )
    var selectedItemIndex = 0
}

@Composable
fun MyPetBottomBar(
    navController: NavHostController,
    profileId: Int?,
    canNavigateBack: Boolean?,
    items: List<BottomNavigationItem>,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                label = { Text(text = stringResource(item.bbroute.title)) },
                selected = BottomBarData.selectedItemIndex == index,
                onClick = {
                    Log.d("my", "sel=${BottomBarData.selectedItemIndex} --- index=$index")
                    BottomBarData.selectedItemIndex = index
                    Log.d("my", "sel=${BottomBarData.selectedItemIndex} --- index=$index")
                    navController.navigate(item.bbroute.route + "/" + profileId + "/" + canNavigateBack) {
                        popUpTo(Routes.ListProfile.route) {
                            inclusive = false
                        }
                        launchSingleTop = true
                        restoreState = true
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
                            painter = painterResource(item.bbroute.icon),
                            contentDescription = stringResource(item.bbroute.title)
                        )
                    }
                }
            )
        }
    }
}
