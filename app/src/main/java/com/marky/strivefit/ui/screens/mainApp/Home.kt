package com.marky.strivefit.ui.screens.mainApp

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Flame
import com.composables.icons.lucide.PersonStanding
import com.composables.icons.lucide.Footprints
import com.marky.strivefit.ui.theme.CommonColors
import com.marky.strivefit.ui.theme.LocalThemeMode
import com.marky.strivefit.ui.theme.ThemeMode

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        WorkoutCard()

        Spacer(modifier = Modifier.height(32.dp))

        // Progress Stats Section
        Text(
            text = "Your Progress",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        ProgressStats()

        Spacer(modifier = Modifier.height(32.dp))

        // Today's Activity Section
        Text(
            text = "Today's Activity",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        ActivitySummary()

        Spacer(modifier = Modifier.height(16.dp))

        // Exercise Summary Section
        Text(
            text = "Exercise Summary",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(16.dp))

        ExerciseSummary()

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun WorkoutCard() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer // Deep purple background as shown in image
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
                text = "Upper Body Strength",
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
                    text = "45 minutes • 6 exercises",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Button(
                    onClick = { /* Start workout */ },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent,
                        contentColor = MaterialTheme.colorScheme.secondaryContainer // Purple color for "Start" text
                    )
                ) {
                    Text(
                        text = "Start",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = 0.2f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(4.dp),
                color = MaterialTheme.colorScheme.secondaryContainer, // Slightly lighter than background
                trackColor = Color(0xFF3A2A47)
            )
        }
    }
}

@Composable
fun ProgressStats() {
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
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatItem(value = "12", label = "Workouts")
                StatItem(value = "345", label = "Minutes")
                StatItem(value = "4", label = "Week Streak")
            }

            Spacer(modifier = Modifier.height(24.dp))

            LinearProgressIndicator(
                progress = 0.6f,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                color = Color(0xFFBF5AF2),
                trackColor = Color(0xFF2C2C2E) // Dark gray track
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "60% to next level",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun StatItem(value: String, label: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = value,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Gray
        )
    }
}

@Composable
fun ActivitySummary() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Calories Burned Card
        ActivityCard(
            icon = { Icon(com.composables.icons.lucide.Lucide.Flame, contentDescription = "Calories", tint = Color.White) },
            value = "356",
            label = "Calories Burned",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        // Steps Card
        ActivityCard(
            icon = { Icon(com.composables.icons.lucide.Lucide.PersonStanding, contentDescription = "Steps", tint = Color.White) },
            value = "7,245",
            label = "Steps",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun ActivityCard(
    icon: @Composable () -> Unit,
    value: String,
    label: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (LocalThemeMode.current){
                ThemeMode.LIGHT -> Color(0xFF1C1C1E)
                ThemeMode.DARK -> MaterialTheme.colorScheme.surfaceVariant
                ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else Color(0xFF1C1C1E)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(Color(0xFF2C2C2E), CircleShape)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                icon()
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = value,
                style = MaterialTheme.typography.headlineLarge,
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
}

@Composable
fun ExerciseSummary() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = when (LocalThemeMode.current){
                ThemeMode.LIGHT -> Color(0xFF1C1C1E)
                ThemeMode.DARK -> MaterialTheme.colorScheme.surfaceVariant
                ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else Color(0xFF1C1C1E)
            }
        )
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            ExerciseItem(
                icon = { Icon(com.composables.icons.lucide.Lucide.Flame, contentDescription = "Morning Workout", tint = Color.White) },
                title = "Morning Workout",
                details = "25 minutes • Upper Body",
                calories = "156 cal"
            )

            Divider()

            ExerciseItem(
                icon = { Icon(com.composables.icons.lucide.Lucide.Footprints, contentDescription = "Lunch Walk", tint = Color.White) },
                title = "Lunch Walk",
                details = "20 minutes • 1.2 miles",
                calories = "110 cal"
            )
        }
    }
}

@Composable
fun ExerciseItem(
    icon: @Composable () -> Unit,
    title: String,
    details: String,
    calories: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(Color(0xFF2C2C2E), CircleShape)
                .clip(CircleShape),
            contentAlignment = Alignment.Center
        ) {
            icon()
        }

        Spacer(modifier = Modifier.width(16.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )

            Text(
                text = details,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }

        Text(
            text = calories,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color(0xFFBF5AF2) // Purple color for calories
        )
    }
}

@Composable
fun Divider() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color(0xFF2C2C2E))
    )
}