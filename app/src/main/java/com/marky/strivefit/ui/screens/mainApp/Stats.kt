package com.marky.strivefit.ui.screens.mainApp

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatsScreen() {
    val selectedTab = remember { mutableStateOf(0) }
    val timeRangeTab = remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Main tabs (Workout, Strength, Activity)
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
                            "Workout",
                            fontWeight = if (selectedTab.value == 0) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                )
                Tab(
                    selected = selectedTab.value == 1,
                    onClick = { selectedTab.value = 1 },
                    text = {
                        Text(
                            "Strength",
                            fontWeight = if (selectedTab.value == 1) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                )
                Tab(
                    selected = selectedTab.value == 2,
                    onClick = { selectedTab.value = 2 },
                    text = {
                        Text(
                            "Activity",
                            fontWeight = if (selectedTab.value == 2) FontWeight.Bold else FontWeight.Normal
                        )
                    },
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Time range tabs (Daily, Weekly, Monthly, Yearly)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                val timeRanges = listOf("Daily", "Weekly", "Monthly", "Yearly")
                timeRanges.forEachIndexed { index, label ->
                    TimeRangeTab(
                        label = label,
                        isSelected = timeRangeTab.value == index,
                        onClick = { timeRangeTab.value = index },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Content based on selected tab
            Box(modifier = Modifier.weight(1f)) {
                when (selectedTab.value) {
                    0 -> WorkoutStatsContent(timeRangeTab.value)
                    1 -> StrengthStatsContent(timeRangeTab.value)
                    2 -> ActivityStatsContent(timeRangeTab.value)
                }
            }
        }
    }
}

@Composable
fun TimeRangeTab(
    label: String,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.clickable { onClick() }
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isSelected) Color(0xFFBF5AF2) else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(2.dp)
                .background(
                    if (isSelected) Color(0xFFBF5AF2) else Color.Transparent
                )
        )
    }
}

@Composable
fun WorkoutStatsContent(timeRangeTab: Int) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            // Workout Consistency Section
            Text(
                text = "Workout Consistency",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Calendar Card
            CalendarCard(timeRangeTab)

            Spacer(modifier = Modifier.height(24.dp))

            // Exercise Progress Section
            Text(
                text = "Exercise Progress",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Exercise progress items
        items(getExerciseProgressSampleData()) { exercise ->
            ExerciseProgressItem(exercise)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun CalendarCard(timeRangeTab: Int) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            // Header with month and view option
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "July 2025",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                TextButton(onClick = { /* View full year */ }) {
                    Text(
                        text = "View Full Year",
                        color = Color(0xFFBF5AF2)
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Days of week header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val daysOfWeek = listOf("S", "M", "T", "W", "T", "F", "S")
                daysOfWeek.forEach { day ->
                    Text(
                        text = day,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Calendar grid - First row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalendarDayItem("30", intensity = 0)
                CalendarDayItem("1", intensity = 1)
                CalendarDayItem("2", intensity = 2)
                CalendarDayItem("3", intensity = 2)
                CalendarDayItem("4", intensity = 0)
                CalendarDayItem("5", intensity = 2)
                CalendarDayItem("6", intensity = 0)
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Calendar grid - Second row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                CalendarDayItem("7", intensity = 0)
                CalendarDayItem("8", intensity = 1)
                CalendarDayItem("9", intensity = 2)
                CalendarDayItem("10", intensity = 2)
                CalendarDayItem("11", intensity = 3)
                CalendarDayItem("12", intensity = 0)
                CalendarDayItem("13", intensity = 0)
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Legend
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                LegendItem("No Activity", 0)
                LegendItem("Light", 1)
                LegendItem("Medium", 2)
                LegendItem("Intense", 3)
            }
        }
    }
}

@Composable
fun CalendarDayItem(date: String, intensity: Int) {
    val backgroundColor = when (intensity) {
        0 -> Color.Transparent
        1 -> Color(0xFF4C6A2C)  // Light green
        2 -> Color(0xFF5F9239)  // Medium green
        3 -> Color(0xFF71AD43)  // Intense green
        else -> Color.Transparent
    }

    Box(
        modifier = Modifier
            .size(36.dp)
            .background(backgroundColor, CircleShape)
            .clip(CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = if (intensity > 0) Color.White else MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun LegendItem(label: String, intensity: Int) {
    val dotColor = when (intensity) {
        0 -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
        1 -> Color(0xFF4C6A2C)  // Light green
        2 -> Color(0xFF5F9239)  // Medium green
        3 -> Color(0xFF71AD43)  // Intense green
        else -> Color.Transparent
    }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(12.dp)
                .background(dotColor, CircleShape)
        )

        Spacer(modifier = Modifier.width(4.dp))

        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
fun ExerciseProgressItem(exercise: ExerciseProgressData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Exercise icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFF2C2C2E),
                        CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Lucide.Dumbbell,
                    contentDescription = "Exercise",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Exercise details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Max: ${exercise.currentMax} ${exercise.unit} (${exercise.change})",
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (exercise.change.startsWith("+")) Color(0xFF71AD43) else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Progress indicator
            LinearProgressIndicator(
                progress = exercise.progress,
                modifier = Modifier
                    .width(60.dp)
                    .height(4.dp),
                color = Color(0xFFBF5AF2),
                trackColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
            )
        }
    }
}

@Composable
fun StrengthStatsContent(timeRangeTab: Int) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            // Strength Progress Section
            Text(
                text = "Strength Progress",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Bench Press Progress Card
            StrengthProgressCard()

            Spacer(modifier = Modifier.height(24.dp))

            // Top Lifts Section
            Text(
                text = "Top Lifts",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // Top lifts items
        items(getTopLiftsSampleData()) { lift ->
            TopLiftItem(lift)
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Composable
fun StrengthProgressCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Exercise title
            Text(
                text = "Bench Press",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = "Max Weight (lbs)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Chart placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
            ) {
                // Line chart - simplified representation
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val heights = listOf(0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.85f)
                    heights.forEachIndexed { index, height ->
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight(height)
                                .background(
                                    if (index == heights.lastIndex) Color(0xFFFF6941) else Color(0xFFFF6941).copy(alpha = 0.5f),
                                    RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                )
                        )

                        if (index < heights.lastIndex) {
                            Spacer(modifier = Modifier.width(2.dp))
                        }
                    }
                }

                // Current indicator
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(Color(0xFFFF6941), CircleShape)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = "Current",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Month labels
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul")
                months.forEach { month ->
                    Text(
                        text = month,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Progress values
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Starting",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )

                    Text(
                        text = "100 lbs",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }

                Column {
                    Text(
                        text = "Current",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Text(
                        text = "185 lbs",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface,
                        textAlign = TextAlign.End,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                TextButton(
                    onClick = { /* Change exercise */ },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color(0xFFFF6941)
                    )
                ) {
                    Text("Change Exercise")
                }
            }
        }
    }
}

@Composable
fun TopLiftItem(lift: TopLiftData) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Exercise icon
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(
                        Color(0xFF2C2C2E),
                        CircleShape
                    )
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Lucide.Dumbbell,
                    contentDescription = "Exercise",
                    tint = Color.White
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            // Exercise details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = lift.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = "Max: ${lift.maxWeight} lbs",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }

            // Position badge
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(Color(0xFFBF5AF2), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "#${lift.rank}",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun ActivityStatsContent(timeRangeTab: Int) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            // Step Count Section
            Text(
                text = "Step Count",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Step Count Card
            ActivityMetricCard(
                value = "8,245",
                change = "+12% vs last week",
                goal = "Goal: 10,000 steps/day",
                isPositive = true,
                color = Color(0xFF3E95FF)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Calories Burned Section
            Text(
                text = "Calories Burned",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Calories Burned Card
            ActivityMetricCard(
                value = "456",
                change = "+23% vs last week",
                goal = "Goal: 500 cal/day",
                isPositive = true,
                color = Color(0xFFBF5AF2)
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Activity Minutes Section
            Text(
                text = "Activity Minutes",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Activity Minutes Card
            ActivityMetricCard(
                value = "32",
                change = "-5% vs last week",
                goal = "Goal: 45 min/day",
                isPositive = false,
                color = Color(0xFFFF9F0A)
            )
        }
    }
}

@Composable
fun ActivityMetricCard(
    value: String,
    change: String,
    goal: String,
    isPositive: Boolean,
    color: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp)
        ) {
            // Value and change indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = change,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if (isPositive) Color(0xFFBF5AF2) else Color(0xFFFF453A)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Chart placeholder
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
            ) {
                // Bar chart - simplified representation
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Bottom
                ) {
                    val heights = listOf(0.4f, 0.6f, 0.7f, 0.5f, 0.8f, 0.45f, 0.3f)
                    val days = listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")

                    heights.forEachIndexed { index, height ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.weight(1f)
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(36.dp)
                                    .fillMaxHeight(height)
                                    .background(
                                        color,
                                        RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp)
                                    )
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = days[index],
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                        }

                        if (index < heights.lastIndex) {
                            Spacer(modifier = Modifier.width(4.dp))
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Goal indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = goal,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                TextButton(
                    onClick = { /* View details */ },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = color
                    )
                ) {
                    Text("View Details")
                }
            }
        }
    }
}

// Sample data for exercise progress
data class ExerciseProgressData(
    val name: String,
    val currentMax: String,
    val unit: String,
    val change: String,
    val progress: Float
)

fun getExerciseProgressSampleData(): List<ExerciseProgressData> {
    return listOf(
        ExerciseProgressData("Bench Press", "185", "lbs", "+25 lbs", 0.85f),
        ExerciseProgressData("Squat", "225", "lbs", "+40 lbs", 0.75f),
        ExerciseProgressData("Deadlift", "275", "lbs", "+50 lbs", 0.9f),
        ExerciseProgressData("Shoulder Press", "95", "lbs", "+15 lbs", 0.65f),
        ExerciseProgressData("Pull-ups", "12", "reps", "+3 reps", 0.5f),
    )
}

// Sample data for top lifts
data class TopLiftData(
    val name: String,
    val maxWeight: Int,
    val rank: Int
)

fun getTopLiftsSampleData(): List<TopLiftData> {
    return listOf(
        TopLiftData("Deadlift", 275, 1),
        TopLiftData("Squat", 225, 2),
        TopLiftData("Bench Press", 185, 3),
        TopLiftData("Barbell Row", 155, 4),
        TopLiftData("Shoulder Press", 95, 5)
    )
}