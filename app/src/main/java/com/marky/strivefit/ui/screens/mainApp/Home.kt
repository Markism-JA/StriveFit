package com.marky.strivefit.ui.screens.mainApp

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.composables.icons.lucide.Flame
import com.composables.icons.lucide.Footprints
import com.composables.icons.lucide.Lucide
import com.composables.icons.lucide.PersonStanding

@Composable
fun HomeScreen() {
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        if (isLandscape) {
            LandscapeHomeScreenContent()
        } else {
            PortraitHomeScreenContent()
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun PortraitHomeScreenContent() {
    WorkoutCard()
    Spacer(modifier = Modifier.height(32.dp))
    ProgressSection()
    Spacer(modifier = Modifier.height(32.dp))
    ActivitySection()
    Spacer(modifier = Modifier.height(16.dp))
    ExerciseSummarySection()
}

@Composable
fun LandscapeHomeScreenContent() {
    WorkoutCard()
    Spacer(modifier = Modifier.height(32.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            ProgressSection()
        }
        Column(modifier = Modifier.weight(1f)) {
            ActivitySection()
        }
    }
    Spacer(modifier = Modifier.height(32.dp))
    ExerciseSummarySection()
}

@Composable
fun ProgressSection() {
    Text(
        text = "Your Progress",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(16.dp))
    ProgressStats()
}

@Composable
fun ActivitySection() {
    Text(
        text = "Today's Activity",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(16.dp))
    ActivitySummary()
}

@Composable
fun ExerciseSummarySection() {
    Text(
        text = "Exercise Summary",
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(16.dp))
    ExerciseSummary()
}


@Composable
fun WorkoutCard() {
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
                        contentColor = MaterialTheme.colorScheme.secondaryContainer
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
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onSurfaceVariant
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
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
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
                    .height(4.dp),
                color = MaterialTheme.colorScheme.primary,
                trackColor = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "60% to next level",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray, // Reference Secondary Text Color
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
        ActivityCard(
            icon = { Icon(Lucide.Flame, contentDescription = "Calories", tint = Color.White) },
            value = "356",
            label = "Calories Burned",
            modifier = Modifier.weight(1f)
        )

        Spacer(modifier = Modifier.width(16.dp))

        ActivityCard(
            icon = { Icon(Lucide.PersonStanding, contentDescription = "Steps", tint = Color.White) }, // Icon tint remains white
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
    val cardBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val iconBackgroundColor = Color(0xFF2C2C2E)

    val valueTextColor = MaterialTheme.colorScheme.onBackground
    val labelTextColor = Color.Gray


    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(iconBackgroundColor, CircleShape)
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
                color = valueTextColor
            )

            Text(
                text = label,
                style = MaterialTheme.typography.bodyMedium,
                color = labelTextColor
            )
        }
    }
}

@Composable
fun ExerciseSummary() {
    val cardBackgroundColor = MaterialTheme.colorScheme.surfaceVariant
    val iconBackgroundColor = Color(0xFF2C2C2E)
    val titleTextColor = MaterialTheme.colorScheme.onBackground
    val detailsTextColor = Color.Gray
    val calorieAccentColor = Color(0xFFBF5AF2)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = cardBackgroundColor
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline)
    ) {
        Column(
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            ExerciseItem(
                icon = { Icon(Lucide.Flame, contentDescription = "Morning Workout", tint = Color.White) },
                title = "Morning Workout",
                details = "25 minutes • Upper Body",
                calories = "156 cal",
                iconBackgroundColor = iconBackgroundColor,
                contentColor = titleTextColor,
                secondaryContentColor = detailsTextColor,
                accentCalorieColor = calorieAccentColor
            )

            Divider(color = iconBackgroundColor)

            ExerciseItem(
                icon = { Icon(Lucide.Footprints, contentDescription = "Lunch Walk", tint = Color.White) },
                title = "Lunch Walk",
                details = "20 minutes • 1.2 miles",
                calories = "110 cal",
                iconBackgroundColor = iconBackgroundColor,
                contentColor = titleTextColor,
                secondaryContentColor = detailsTextColor,
                accentCalorieColor = calorieAccentColor
            )
        }
    }
}

@Composable
fun ExerciseItem(
    icon: @Composable () -> Unit,
    title: String,
    details: String,
    calories: String,
    iconBackgroundColor: Color,
    contentColor: Color,
    secondaryContentColor: Color,
    accentCalorieColor: Color
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
                .background(iconBackgroundColor, CircleShape)
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
                color = contentColor
            )

            Text(
                text = details,
                style = MaterialTheme.typography.bodyMedium,
                color = secondaryContentColor
            )
        }

        Text(
            text = calories,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = accentCalorieColor
        )
    }
}

@Composable
fun Divider(modifier: Modifier = Modifier, color: Color? = null) {
    val dividerColor = color ?: Color(0xFF2C2C2E)
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(MaterialTheme.colorScheme.outline)
    )
}