package com.f4.mypet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.f4.mypet.navigation.SetupNavGraph
import com.f4.mypet.ui.theme.MyPetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPetTheme {
                SetupNavGraph(navController = rememberNavController())
            }
        }
    }
}
