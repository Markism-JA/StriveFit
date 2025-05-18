package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.composables.icons.lucide.Box
import com.composables.icons.lucide.ChartColumnIncreasing
import com.composables.icons.lucide.CircleUserRound
import com.composables.icons.lucide.ClipboardList
import com.composables.icons.lucide.Clock11
import com.composables.icons.lucide.House
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.Moon
import com.composables.icons.lucide.Mountain
import com.composables.icons.lucide.Settings
import com.composables.icons.lucide.SunMedium
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.GoBackButton
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun WorkoutPreferencesScreen(
    onBackClick: () -> Unit = {},
    onCreatePlanClick: () -> Unit = {}
) {
    // State for workout preferences
    var workoutsPerWeek by remember { mutableStateOf(4) }
    var workoutDurationMinutes by remember { mutableStateOf(45) }
    var preferredHour by remember { mutableStateOf(8) }
    var preferredMinute by remember { mutableStateOf(0) }
    var isAm by remember { mutableStateOf(true) }
    var showTimePicker by remember { mutableStateOf(false) }

    // Time formatter helper function using Calendar (compatible with older Android versions)
    fun formatTime(hour: Int, minute: Int, isAm: Boolean): String {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, if (isAm) {
            if (hour == 12) 0 else hour
        } else {
            if (hour == 12) 12 else hour + 12
        })
        calendar.set(Calendar.MINUTE, minute)

        val formatter = SimpleDateFormat("h:mm a", Locale.US)
        return formatter.format(calendar.time)
    }

    // Function to add minutes to a time
    fun addMinutes(hour: Int, minute: Int, isAm: Boolean, minutesToAdd: Int): Triple<Int, Int, Boolean> {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, if (isAm) {
            if (hour == 12) 0 else hour
        } else {
            if (hour == 12) 12 else hour + 12
        })
        calendar.set(Calendar.MINUTE, minute)
        calendar.add(Calendar.MINUTE, minutesToAdd)

        val resultHour = calendar.get(Calendar.HOUR)
        val resultMinute = calendar.get(Calendar.MINUTE)
        val resultIsAm = calendar.get(Calendar.AM_PM) == Calendar.AM

        return Triple(
            if (resultHour == 0) 12 else resultHour,
            resultMinute,
            resultIsAm
        )
    }

    // Calculate start and end times
    val startTimeFormatted = formatTime(preferredHour, preferredMinute, isAm)
    val (endHour, endMinute, endIsAm) = addMinutes(preferredHour, preferredMinute, isAm, workoutDurationMinutes)
    val endTimeFormatted = formatTime(endHour, endMinute, endIsAm)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    GoBackButton(
                        onClick = onBackClick,
                        modifier = Modifier.align(Alignment.CenterStart)
                    )


                    Text(
                        text = "Workout Preferences",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(start = 30.dp)
                    )
                }

                Text(
                    text = "Set your workout preferences",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                )

                LinearProgressIndicator(
                    progress = { 1.0f }, // 5 of 5 steps complete
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(4.dp),
                    color = MaterialTheme.colorScheme.primary,
                    trackColor = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Text(
                        text = "Step 8 of 8",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Workout preferences content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                // Workouts per week slider
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Workouts per week",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "2",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        // Thinner slider without dots
                        Slider(
                            value = workoutsPerWeek.toFloat(),
                            onValueChange = { workoutsPerWeek = it.toInt() },
                            valueRange = 2f..7f,
                            steps = 0, // Remove step dots
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                                .height(16.dp), // Reduce height for thinner slider
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.primary,
                                inactiveTrackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                            )
                        )

                        Text(
                            text = "7",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        text = "$workoutsPerWeek days",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Workout duration slider
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Workout duration",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "15m",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )

                        // Thinner slider without dots
                        Slider(
                            value = workoutDurationMinutes.toFloat(),
                            onValueChange = { workoutDurationMinutes = it.toInt() },
                            valueRange = 15f..90f,
                            steps = 0, // Remove step dots
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 8.dp)
                                .height(16.dp), // Reduce height for thinner slider
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.primary,
                                activeTrackColor = MaterialTheme.colorScheme.primary,
                                inactiveTrackColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                            )
                        )

                        Text(
                            text = "90m",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground
                        )
                    }

                    Text(
                        text = "$workoutDurationMinutes minutes",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                // Preferred workout time with clock picker
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Preferred workout time",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Time picker field
                    TimePickerField(
                        timeText = startTimeFormatted,
                        onClick = { showTimePicker = true }
                    )

                    // Display selected time range
                    Text(
                        text = "Workout time: $startTimeFormatted - $endTimeFormatted",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 16.dp)
                    )

                    Text(
                        text = "We will notify you at this time",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp)
                    )
                }
            }

            // Create plan button
            AnimatedCustomButton(
                onClick = onCreatePlanClick,
                text = "Create My Plan",
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            )
        }

        // Time picker dialog
        if (showTimePicker) {
            TimePickerDialog(
                initialHour = preferredHour,
                initialMinute = preferredMinute,
                initialIsAm = isAm,
                onTimeSelected = { hour, minute, am ->
                    preferredHour = hour
                    preferredMinute = minute
                    isAm = am
                    showTimePicker = false
                },
                onDismiss = { showTimePicker = false }
            )
        }
    }
}

@Composable
fun TimePickerField(
    timeText: String,
    onClick: () -> Unit
) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        tonalElevation = 4.dp,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = Lucide.Clock11,
                contentDescription = "Clock",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )


            Spacer(modifier = Modifier.width(8.dp))

            Text(
                text = timeText,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Select time",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun TimePickerDialog(
    initialHour: Int,
    initialMinute: Int,
    initialIsAm: Boolean,
    onTimeSelected: (hour: Int, minute: Int, isAm: Boolean) -> Unit,
    onDismiss: () -> Unit
) {
    var hour by remember { mutableStateOf(initialHour) }
    var minute by remember { mutableStateOf(initialMinute) }
    var isAm by remember { mutableStateOf(initialIsAm) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 8.dp
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Select Workout Time",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Clock Display (Simplified for this example)
                ClockDisplay(
                    hour = hour,
                    minute = minute,
                    isAm = isAm,
                    onHourChange = { hour = it },
                    onMinuteChange = { minute = it }
                )

                Spacer(modifier = Modifier.height(24.dp))

                // AM/PM toggle
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    AmPmToggle(
                        isAm = isAm,
                        onToggle = { isAm = it }
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Buttons
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    Button(
                        onClick = { onTimeSelected(hour, minute, isAm) },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

@Composable
fun ClockDisplay(
    hour: Int,
    minute: Int,
    isAm: Boolean,
    onHourChange: (Int) -> Unit,
    onMinuteChange: (Int) -> Unit
) {
    // Display hour in 12-hour format for display
    val displayHour = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Hour picker
        NumberPicker(
            value = displayHour,
            onValueChange = { newHour ->
                // Convert from 12-hour display to 24-hour internal format
                val actual = if (newHour == 12) {
                    if (isAm) 0 else 12
                } else {
                    if (isAm) newHour else newHour + 12
                }
                onHourChange(actual)
            },
            range = 1..12
        )

        Text(
            text = ":",
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        // Minute picker
        NumberPicker(
            value = minute,
            onValueChange = onMinuteChange,
            range = 0..59,
            formatter = { if (it < 10) "0$it" else "$it" }
        )
    }
}

@Composable
fun NumberPicker(
    value: Int,
    onValueChange: (Int) -> Unit,
    range: IntRange,
    formatter: (Int) -> String = { it.toString() }
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = {
                val newValue = if (value + 1 > range.last) range.first else value + 1
                onValueChange(newValue)
            }
        ) {
            Text(
                text = "▲",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }

        Text(
            text = formatter(value),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.onSurface
        )

        IconButton(
            onClick = {
                val newValue = if (value - 1 < range.first) range.last else value - 1
                onValueChange(newValue)
            }
        ) {
            Text(
                text = "▼",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun AmPmToggle(
    isAm: Boolean,
    onToggle: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(24.dp))
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(if (isAm) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable { onToggle(true) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "AM",
                style = MaterialTheme.typography.bodyLarge,
                color = if (isAm) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(
            modifier = Modifier
                .width(80.dp)
                .height(40.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(if (!isAm) MaterialTheme.colorScheme.primary else Color.Transparent)
                .clickable { onToggle(false) },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "PM",
                style = MaterialTheme.typography.bodyLarge,
                color = if (!isAm) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWorkoutPreferencesScreen() {
    MaterialTheme {
        WorkoutPreferencesScreen()
    }
}