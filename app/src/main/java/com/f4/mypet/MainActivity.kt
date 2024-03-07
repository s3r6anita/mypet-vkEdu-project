package com.f4.mypet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.f4.mypet.ui.screens.profile.CreateUpdateProfileScreen
import com.f4.mypet.ui.screens.profile.ListProfileScreen
import com.f4.mypet.ui.screens.profile.ProfileScreen
import com.f4.mypet.ui.theme.MyPetTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPetTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ListProfileScreen()
                }
            }
        }
    }
}
