package com.f4.mypet.ui.screens.bugReport

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.navigation.Routes
import com.f4.mypet.R
import com.f4.mypet.ui.screens.bugReport.screenComponents.BugReportDescriptionField
import com.f4.mypet.ui.screens.bugReport.screenComponents.BugReportErrorField
import com.f4.mypet.ui.screens.bugReport.screenComponents.CustomAlertDialog
import com.f4.mypet.ui.screens.bugReport.screenComponents.SendButton

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter", "IntentReset",
    "UnusedMaterial3ScaffoldPaddingParameter"
)
@Composable
fun BugReportScreen(navController: NavHostController, context: Context) {
    //val context = LocalContext.current
    var mutableSubject by remember { mutableStateOf("") }
    var mutableMessage by remember { mutableStateOf("") }
    var openAlertDialog by remember { mutableStateOf(false) }

    val email = stringResource(R.string.bug_report_email)

    when {
        openAlertDialog -> {
            CustomAlertDialog(
                onDismissRequest = { openAlertDialog = false },
                onConfirmation = {
                    openAlertDialog = false

                    navController.navigate(Routes.ListProfile.route) {
                        popUpTo(Routes.ListProfile.route) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }

                    // логика создания наполнения письма и его отправки

                    val intent = Intent(Intent.ACTION_SENDTO).apply {
                        type = "text/plain"
                        data = Uri.parse("mailto:$email?subject=$mutableSubject&body=$mutableMessage")
                    }

                    try{
                        context.startActivity(Intent.createChooser(intent, "Send Email"))
                    } catch (e: Exception) {
                        Toast.makeText(context, context.getString(R.string.bug_report_toast_error), Toast.LENGTH_SHORT).show()
                    }
                },
                dialogTitle = context.getString(R.string.bug_report_dialog_title),
                dialogText = context.getString(R.string.bug_report_dialog_text)
            )
        }
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = stringResource(R.string.bug_report_title),
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() },
                actions = {}
            )
        },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Box() {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Top
                ) {
                    BugReportErrorField(value = mutableSubject, onValueChange = {mutableSubject = it})
                    BugReportDescriptionField(value = mutableMessage, onValueChange = { mutableMessage = it })
                }

            }
            SendButton(
                onClickAction = { openAlertDialog = true }
            )
        }
    }
}


