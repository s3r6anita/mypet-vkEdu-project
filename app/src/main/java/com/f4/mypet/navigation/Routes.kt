package com.f4.mypet.navigation

import androidx.annotation.StringRes
import com.f4.mypet.R

const val START = "start"

sealed class Routes(
    val route: String,
    @StringRes val title: Int
) {
    data object ListProfile : Routes("ListProfile", R.string.list_profile_screen_title)
    data object CreateProfile : Routes("CreateProfile", R.string.create_profile_screen_title)
    data object UpdateProfile : Routes("UpdateProfile", R.string.update_profile_screen_title)

    data object Procedure : Routes("Procedure", R.string.procedure_screen_title)
    data object CreateProcedure : Routes("CreateProcedure", R.string.create_procedure_screen_title)
    data object UpdateProcedure : Routes("UpdateProcedure", R.string.update_procedure_screen_title)

    sealed class BottomBarRoutes(
        route: String, title: Int
    ) : Routes(route, title
    ) {
        data object ListProcedures : BottomBarRoutes("ListProcedures", R.string.list_procedure_screen_title,)
        data object MedCard : BottomBarRoutes("MedCard", R.string.medcard_screen_title)
        data object Profile : BottomBarRoutes("Profile", R.string.profile_screen_title)
    }
}
