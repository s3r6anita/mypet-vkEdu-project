package com.f4.mypet.ui.screens.procedure.createUpdate.success

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.app.NotificationCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.f4.mypet.MainActivity
import com.f4.mypet.R
import com.f4.mypet.navigation.Routes
import com.f4.mypet.ui.components.MyPetTopBar
import com.f4.mypet.ui.screens.LoadingScreen
import com.f4.mypet.ui.screens.procedure.createUpdate.CreateUpdateProcedureViewModel
import com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents.DatePickerSelector
import com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents.FrequencySelector
import com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents.NotificationsSelector
import com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents.SelectProcedureType
import com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents.TimeDoneSelector
import com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents.getDropdownMenuColors
import com.f4.mypet.ui.screens.procedure.createUpdate.success.screenComponents.getOutLinedTextFieldColors
import com.f4.mypet.ui.theme.GreenButton
import com.f4.mypet.util.validate
import kotlinx.collections.immutable.toImmutableList
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.ZonedDateTime

fun scheduleNotification(context: Context, title: String, message: String, notificationTime: LocalDateTime, notificationId: Int) {
    val intent = Intent(context, AlarmReceiver::class.java).apply {
        putExtra("notification_title", title)
        putExtra("notification_message", message)
        putExtra("notification_id", notificationId) // Добавим notificationId в intent
    }
    val pendingIntent = PendingIntent.getBroadcast(
        context, notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    val systemZoneId = ZoneId.systemDefault()
    val systemZonedDateTime = notificationTime.atZone(systemZoneId)
    val triggerAtMillis = systemZonedDateTime.toInstant().toEpochMilli()

    alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
}
fun createNotificationChannel(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channelId = "notification_channel1"
        val channelName = "My Notification Channel1"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
            description = "Channel description"
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
}
class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("notification_title") ?: "Процедура"
        val message = intent.getStringExtra("notification_message") ?: "Время выполнить процедуру"
        val notificationId = intent.getIntExtra("notification_id", 0) // Получим notificationId из intent

        // Создаем намерение для запуска MainActivity с необходимым маршрутом
        val resultIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("destination_route", "${Routes.Procedure.route}/$notificationId")
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            resultIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(context, "notification_channel1")
            .setSmallIcon(R.drawable.pet_icon)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent) // Устанавливаем PendingIntent для уведомления
            .setAutoCancel(true) // Автоматическое закрытие уведомления после нажатия
            .build()

        notificationManager.notify(notificationId, notification) // Используем notificationId для уведомления
    }
}
@Suppress("CyclomaticComplexMethod", "LongMethod")
@Composable
fun SuccessCUProcedureScreen(
    navController: NavHostController,
    isCreateScreen: Boolean,
    profileId: Int,
    viewModel: CreateUpdateProcedureViewModel = hiltViewModel(),
) {
    val types = viewModel.types

    val procedureDB by viewModel.procedureUiState.collectAsState()

    val type by remember { mutableStateOf(viewModel.type) }
    var title by remember { mutableStateOf(viewModel.title) }
    var procedure by remember { mutableStateOf(procedureDB) }

    LaunchedEffect(procedureDB) {
        procedure = procedureDB
    }

    Scaffold(
        topBar = {
            MyPetTopBar(
                text = if (isCreateScreen) stringResource(Routes.CreateProcedure.title) else stringResource(
                    Routes.UpdateProcedure.title
                ),
                canNavigateBack = true,
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
            verticalArrangement = Arrangement.Center
        ) {

            // тип процедуры
            var selectedType by remember {
                mutableStateOf(
                    if (isCreateScreen) types.first()
                    else type
                )
            }
            SelectProcedureType(
                types = types.toImmutableList(),
                selectedType = selectedType,
                dropdownMenuColors = getDropdownMenuColors(),
                changeSelectedType = { newType ->
                    selectedType = newType
                    title = title.copy(type = newType.id)
                },
            )

            // Название процедуры
            var titleIsCorrect by remember { mutableStateOf(!isCreateScreen) }
            // TODO: сделать чтобы при вводе тайтла выпадали последние введенные

            OutlinedTextField(
                value = title.name,
                onValueChange = {
                    titleIsCorrect = validate(it)
                    title = title.copy(name = it)
                },
                singleLine = true,
                label = { Text(stringResource(R.string.creation_procedure_screen_name)) },
                modifier = Modifier
                    .padding(bottom = 10.dp, start = 30.dp, end = 30.dp)
                    .fillMaxWidth(),
                isError = !titleIsCorrect,
            )

            // периодичность - выпадающее меню с выбором
            FrequencySelector(
                procedure = procedure,
                isCreateScreen = isCreateScreen,
                onProcedureChange = { newProcedure ->
                    procedure = newProcedure
                }
            )

            // Время выполнения - тайм пикер
            TimeDoneSelector(
                procedure = procedure,
                onProcedureChange = { newProcedure ->
                    procedure = newProcedure
                }
            )

            // дата выполнения
            DatePickerSelector(
                procedure = procedure,
                onProcedureChange = { newProcedure ->
                    procedure = newProcedure
                }
            )

            // уведомление
            NotificationsSelector(
                procedure = procedure,
                onProcedureChange = { newProcedure ->
                    procedure = newProcedure
                }
            )

            // заметки
            OutlinedTextField(
                value = procedure.notes,
                onValueChange = { procedure = procedure.copy(notes = it) },
                label = { Text(stringResource(id = R.string.creation_procedure_screen_notes)) },
                trailingIcon = {
                    IconButton(onClick = { procedure = procedure.copy(notes = "") }) {
                        Icon(
                            Icons.Default.Clear,
                            contentDescription = stringResource(id = R.string.clear)
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp),
                singleLine = false,
                shape = RoundedCornerShape(12.dp),
                colors = getOutLinedTextFieldColors()
            )
            val createDelayedNotification = remember { mutableStateOf(false) }
            if (createDelayedNotification.value) {
                val titleNotification = if (title.name.isBlank()) "Процедура" else title.name
                val timeMessage = procedure.dateDone.format(PetDateTimeFormatter.date)
                val dateMessage = procedure.dateDone.format(PetDateTimeFormatter.time)
                val message = "Напоминание: дата выполнения процедуры - $timeMessage. Время - $dateMessage"
                val procedureId = procedure.id
                // Получаем дату и время из процедуры
                val reminderDate = procedure.reminder!!.format(PetDateTimeFormatter.date)
                val reminderTime = procedure.reminder!!.format(PetDateTimeFormatter.time)

                // Создаем LocalDateTime из даты и времени
                val reminderDateTime = LocalDateTime.of(
                    LocalDate.parse(reminderDate, PetDateTimeFormatter.date), // Преобразуем дату из строки в LocalDate
                    LocalTime.parse(reminderTime, PetDateTimeFormatter.time)  // Преобразуем время из строки в LocalTime
                )
                // Логирование времени перед передачей в scheduleNotification
                scheduleNotification(context, titleNotification, message, reminderDateTime, procedureId)
                createDelayedNotification.value = false
            }
            // сохранение
            Button(
                modifier = Modifier.padding(20.dp),
                onClick = {
                    if (isCreateScreen) {
                        procedure = procedure.copy(pet = profileId)
                        title = title.copy(type = selectedType.id)
                        viewModel.createProcedure(procedure, title)

                    } else {
                        title = title.copy(type = selectedType.id)
                        viewModel.updateProcedure(procedure, title)
                    }
                    if (enableNotifications) {
                        createDelayedNotification.value = true
                    }
                },
                border = BorderStroke(1.dp, GreenButton),
                colors = ButtonDefaults.buttonColors(containerColor = GreenButton)
            ) {
                Text(
                    text = stringResource(id = R.string.save_button_description),
                    Modifier.padding(start = 10.dp),
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}
