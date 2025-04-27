package com.marky.strivefit.ui.screens.mainApp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.*
import com.marky.strivefit.ui.theme.LocalThemeMode
import com.marky.strivefit.ui.theme.ThemeMode
import kotlinx.coroutines.launch
import java.time.DayOfWeek
import java.time.format.TextStyle
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutScreen(
    navigateToExerciseDatabase: () -> Unit = {},
    navigateToCreateWorkout: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val selectedTab = remember { mutableStateOf(0) }
    val systemAssistEnabled = remember { mutableStateOf(true) }
    val showFeedbackDialog = remember { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    val showBottomSheet = remember { mutableStateOf(false) }
    val darkCardColor = when (LocalThemeMode.current) {
        ThemeMode.LIGHT -> Color(0xFF1C1C1E)
        ThemeMode.DARK -> MaterialTheme.colorScheme.surfaceVariant
        ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else Color(0xFF1C1C1E)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {


            // Tabs
            TabRow(
                selectedTabIndex = selectedTab.value,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                indicator = { },
                divider = { }
            ) {
                Tab(
                    selected = selectedTab.value == 0,
                    onClick = { selectedTab.value = 0 },
                    text = {
                        Text(
                            "System Recommended",
                            fontWeight = if (selectedTab.value == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                )
                Tab(
                    selected = selectedTab.value == 1,
                    onClick = { selectedTab.value = 1 },
                    text = {
                        Text(
                            "My Workouts",
                            fontWeight = if (selectedTab.value == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Box(modifier = Modifier.weight(1f)) {
                when (selectedTab.value) {
                    0 -> SystemRecommendedContent(
                        darkCardColor = darkCardColor,
                        systemAssistEnabled = systemAssistEnabled,
                        onReassess = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Reassessing workout plan based on latest data...")
                            }
                        },
                        onSavePlan = {
                            coroutineScope.launch {
                                snackbarHostState.showSnackbar("Workout plan saved to My Workouts")
                            }
                        },
                        onSystemAssistToggle = {
                            systemAssistEnabled.value = it
                        },
                        onFeedbackRequest = {
                            showFeedbackDialog.value = true
                        }
                    )
                    1 -> UserWorkoutsContent(
                        darkCardColor = darkCardColor,
                        onEditWorkout = { workoutId ->
                            // Navigate to edit workout
                        }
                    )
                }
            }
        }

        // Snackbar host
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        )

        FloatingActionButton(
            onClick = { showBottomSheet.value = true },
            modifier = Modifier
                .padding(24.dp)
                .align(Alignment.BottomEnd),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) {
            Icon(
                Lucide.Plus,
                contentDescription = "Create Workout"
            )
        }
        // Feedback Dialog
        if (showFeedbackDialog.value) {
            FeedbackDialog(
                onDismiss = { showFeedbackDialog.value = false },
                onSubmit = { feedback ->
                    showFeedbackDialog.value = false
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Thank you for your feedback! We'll adjust your next workout.")
                    }
                }
            )
        }

        // Bottom Sheet for creating a new workout
        if (showBottomSheet.value) {
            ModalBottomSheet(
                onDismissRequest = { showBottomSheet.value = false },
                sheetState = bottomSheetState
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                ) {
                    Text(
                        "Create Workout Plan",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Button(
                        onClick = {
                            showBottomSheet.value = false
                            navigateToCreateWorkout()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Icon(
                            Lucide.Plus,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Create Custom Workout")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            showBottomSheet.value = false
                            navigateToExerciseDatabase()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onBackground
                        )
                    ) {
                        Icon(
                            Lucide.Database,
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Browse Exercise Database")
                    }

                    Spacer(modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}


@Composable
fun DayItem(
    day: String,
    date: String,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(12.dp))
            .clickable { onSelected() }
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer else Color.Transparent,
                RoundedCornerShape(12.dp)
            )
            .padding(8.dp)
    ) {
        Text(
            text = day,
            style = MaterialTheme.typography.bodyMedium,
            color = if (isSelected) MaterialTheme.colorScheme.onPrimaryContainer else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .size(36.dp)
                .background(
                    if (isSelected) Color(0xFFBF5AF2) else MaterialTheme.colorScheme.surfaceVariant,
                    CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = date,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SystemRecommendedContent(
    darkCardColor: Color,
    systemAssistEnabled: MutableState<Boolean>,
    onReassess: () -> Unit,
    onSavePlan: () -> Unit,
    onSystemAssistToggle: (Boolean) -> Unit,
    onFeedbackRequest: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            // System Controls
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(24.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "System Assistance",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Switch(
                            checked = systemAssistEnabled.value,
                            onCheckedChange = {
                                systemAssistEnabled.value = it
                                onSystemAssistToggle(it)
                            },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = Color(0xFFBF5AF2),
                                uncheckedThumbColor = Color.White,
                                uncheckedTrackColor = Color(0xFF3A2A47)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Button(
                            onClick = onReassess,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFF3A2A47),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Lucide.RefreshCcw,
                                contentDescription = "Reassess",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Reassess")
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Button(
                            onClick = onSavePlan,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFBF5AF2),
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Icon(
                                Lucide.Save,
                                contentDescription = "Save Plan",
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text("Save Plan")
                        }
                    }

                    AnimatedVisibility(visible = systemAssistEnabled.value) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Spacer(modifier = Modifier.height(16.dp))

                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = Color(0xFF3A2A47)
                                ),
                                shape = RoundedCornerShape(12.dp)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(
                                        Lucide.MessageSquare,
                                        contentDescription = "Feedback",
                                        tint = Color(0xFFBF5AF2),
                                        modifier = Modifier.size(24.dp)
                                    )

                                    Spacer(modifier = Modifier.width(12.dp))

                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            "How was your last workout?",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.White
                                        )
                                        Text(
                                            "Provide feedback to improve recommendations",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = Color.Gray
                                        )
                                    }

                                    IconButton(onClick = onFeedbackRequest) {
                                        Icon(
                                            Lucide.ChevronRight,
                                            contentDescription = "Give Feedback",
                                            tint = Color.White,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Workout Plan
            Text(
                text = "Tracking",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Updated Workout Card for Today
            WorkoutPlanCard(
                darkCardColor = darkCardColor,
                workoutName = "Upper Body Strength",
                duration = "45 min",
                exerciseCount = 6,
                intensity = "Moderate",
                progress = 0.2f
            )
        }

        item {
            Spacer(modifier = Modifier.height(24.dp))

            // Exercise List Header
            Text(
                text = "Exercises",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Exercise Items
        items(getExerciseSampleData()) { exercise ->
            ExerciseItem(
                darkCardColor = darkCardColor,
                exerciseName = exercise.name,
                sets = exercise.sets,
                reps = exercise.reps,
                isCompleted = exercise.isCompleted
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun UserWorkoutsContent(
    darkCardColor: Color,
    onEditWorkout: (String) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(getUserWorkoutsSampleData()) { workout ->
            SavedWorkoutItem(
                darkCardColor = darkCardColor,
                workoutName = workout.name,
                targetMuscle = workout.targetMuscle,
                exerciseCount = workout.exerciseCount,
                duration = workout.duration,
                onEdit = { onEditWorkout(workout.id) }
            )
        }
    }
}

@Composable
fun WorkoutPlanCard(
    darkCardColor: Color,
    workoutName: String,
    duration: String,
    exerciseCount: Int,
    intensity: String,
    progress: Float
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            Text(
                text = "Today's Workout",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = workoutName,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "$duration • $exerciseCount exercises",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Button(
                    onClick = { /* Start workout */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = Color(0xFFBF5AF2) // Purple accent color
                    )
                ) {
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = Color(0xFFBF5AF2), // Purple accent color
                trackColor = Color(0xFF3A2A47) // Darker purple for track
            )
        }
    }
}

@Composable
fun MetricItem(label: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray
        )
    }
}

@Composable
fun ExerciseItem(
    darkCardColor: Color,
    exerciseName: String,
    sets: Int,
    reps: Int,
    isCompleted: Boolean
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = darkCardColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        if (isCompleted) Color(0xFFBF5AF2) else Color(0xFF2C2C2E),
                        CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                if (isCompleted) {
                    Icon(
                        Lucide.Check,
                        contentDescription = "Completed",
                        tint = Color.White
                    )
                } else {
                    Icon(
                        Lucide.Dumbbell,
                        contentDescription = "Exercise",
                        tint = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = exerciseName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Text(
                    text = "$sets sets × $reps reps",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }

            IconButton(onClick = { /* Show exercise details */ }) {
                Icon(
                    Lucide.ChevronRight,
                    contentDescription = "Details",
                    tint = Color.Gray
                )
            }
        }
    }
}

@Composable
fun SavedWorkoutItem(
    darkCardColor: Color,
    workoutName: String,
    targetMuscle: String,
    exerciseCount: Int,
    duration: String,
    onEdit: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(
            containerColor = darkCardColor
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = workoutName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = targetMuscle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFFBF5AF2)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Lucide.Dumbbell,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = "$exerciseCount exercises",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Icon(
                        Lucide.Clock,
                        contentDescription = null,
                        tint = Color.Gray,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = duration,
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            Row {
                IconButton(
                    onClick = { /* Start workout */ },
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color(0xFFBF5AF2), CircleShape)
                ) {
                    Icon(
                        Lucide.Play,
                        contentDescription = "Start",
                        tint = Color.White,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                IconButton(
                    onClick = onEdit,
                    modifier = Modifier
                        .size(40.dp)
                        .border(1.dp, Color.Gray.copy(alpha = 0.5f), CircleShape)
                ) {
                    Icon(
                        Lucide.Pen,
                        contentDescription = "Edit",
                        tint = Color.Gray,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun FeedbackDialog(
    onDismiss: () -> Unit,
    onSubmit: (String) -> Unit
) {
    val feedbackOptions = listOf(
        "Too easy",
        "Just right",
        "Too difficult",
        "Felt pain/discomfort",
        "Need more variety"
    )
    var selectedFeedback by remember { mutableStateOf<String?>(null) }
    var additionalNotes by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Workout Feedback")
        },
        text = {
            Column {
                Text(
                    "How was your workout experience?",
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    feedbackOptions.forEach { option ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (selectedFeedback == option) MaterialTheme.colorScheme.primaryContainer
                                    else MaterialTheme.colorScheme.surfaceVariant
                                )
                                .clickable { selectedFeedback = option }
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = selectedFeedback == option,
                                onClick = { selectedFeedback = option },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = Color(0xFFBF5AF2)
                                )
                            )

                            Spacer(modifier = Modifier.width(8.dp))

                            Text(
                                text = option,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = additionalNotes,
                    onValueChange = { additionalNotes = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Additional notes (optional)") },
                    minLines = 3,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color(0xFFBF5AF2),
                        cursorColor = Color(0xFFBF5AF2)
                    )
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    val feedback = selectedFeedback?.let { "$it: $additionalNotes" } ?: additionalNotes
                    onSubmit(feedback)
                },
                enabled = selectedFeedback != null || additionalNotes.isNotBlank(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFBF5AF2)
                )
            ) {
                Text("Submit")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

// Sample data for previews
data class ExerciseData(
    val name: String,
    val sets: Int,
    val reps: Int,
    val isCompleted: Boolean
)

data class WorkoutData(
    val id: String,
    val name: String,
    val targetMuscle: String,
    val exerciseCount: Int,
    val duration: String
)

fun getExerciseSampleData(): List<ExerciseData> {
    return listOf(
        ExerciseData("Bench Press", 3, 10, true),
        ExerciseData("Shoulder Press", 3, 12, false),
        ExerciseData("Incline Dumbbell Press", 3, 10, false),
        ExerciseData("Tricep Extensions", 3, 15, false),
        ExerciseData("Lat Pulldowns", 3, 12, false),
        ExerciseData("Cable Rows", 3, 12, false)
    )
}

fun getUserWorkoutsSampleData(): List<WorkoutData> {
    return listOf(
        WorkoutData("1", "Lower Body Focus", "Legs, Glutes", 8, "45 min"),
        WorkoutData("2", "Core Blaster", "Abs, Lower Back", 6, "30 min"),
        WorkoutData("3", "Upper Body Strength", "Chest, Shoulders", 7, "50 min"),
        WorkoutData("4", "Full Body Circuit", "Total Body", 10, "60 min"),
        WorkoutData("5", "HIIT Cardio", "Cardio, Endurance", 12, "40 min"),
        WorkoutData("6", "Back & Biceps", "Back, Arms", 6, "35 min"),
        WorkoutData("7", "Push Day", "Chest, Shoulders, Triceps", 7, "45 min"),
        WorkoutData("8", "Pull Day", "Back, Biceps", 6, "40 min")
    )
}