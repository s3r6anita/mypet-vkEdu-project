package com.f4.mypet.navigation

import androidx.annotation.StringRes
import com.f4.mypet.R

const val START = "start"

sealed class Routes(
    val route: String,
    @StringRes val title: Int
) {
    object ListProfile : Routes("ListProfile", R.string.list_profile_screen_title)
    object Profile : Routes("Profile", R.string.profile_screen_title)
    object CreateProfile : Routes("CreateProfile", R.string.create_profile_screen_title)
    object UpdateProfile : Routes("UpdateProfile", R.string.update_profile_screen_title)

    object ListProcedure: Routes("ListProcedure", R.string.list_procedure_screen_title)
    object Procedure: Routes("Procedure", R.string.procedure_screen_title)
    object CreateProcedure: Routes("CreateProcedure", R.string.create_procedure_screen_title)
    object UpdateProcedure: Routes("UpdateProcedure", R.string.update_procedure_screen_title)
}
