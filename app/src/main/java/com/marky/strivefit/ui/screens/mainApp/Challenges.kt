package com.marky.strivefit.ui.screens.mainApp


import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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

@Composable
fun ChallengesScreen() {
    val selectedTab = remember { mutableStateOf(0) }
    val darkCardColor = when (LocalThemeMode.current) {
        ThemeMode.LIGHT -> Color(0xFF1C1C1E)
        ThemeMode.DARK -> MaterialTheme.colorScheme.surfaceVariant
        ThemeMode.SYSTEM -> if (isSystemInDarkTheme()) MaterialTheme.colorScheme.surfaceVariant else Color(0xFF1C1C1E)
    }

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
                        "Active",
                        fontWeight = if (selectedTab.value == 0) FontWeight.Bold else FontWeight.Normal
                    )
                },
            )
            Tab(
                selected = selectedTab.value == 1,
                onClick = { selectedTab.value = 1 },
                text = {
                    Text(
                        "Completed",
                        fontWeight = if (selectedTab.value == 1) FontWeight.Bold else FontWeight.Normal
                    )
                },
            )
            Tab(
                selected = selectedTab.value == 2,
                onClick = { selectedTab.value = 2 },
                text = {
                    Text(
                        "Browse",
                        fontWeight = if (selectedTab.value == 2) FontWeight.Bold else FontWeight.Normal
                    )
                },
            )
        }

        Spacer(modifier = Modifier.height(24.dp))


        // Content based on selected tab
        when (selectedTab.value) {
            0 -> ActiveChallengesContent(darkCardColor)
            1 -> CompletedChallengesContent(darkCardColor)
            2 -> BrowseChallengesContent(darkCardColor)
        }
    }
}

@Composable
fun ActiveChallengesContent(darkCardColor: Color) {
    val activeChallenges = remember { getActiveChallengesSampleData() }

    if (activeChallenges.isEmpty()) {
        EmptyChallengesState(
            message = "You don't have any active challenges",
            subMessage = "Join a challenge to start tracking your progress"
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(activeChallenges) { challenge ->
                ChallengeCard(
                    darkCardColor = darkCardColor,
                    challengeName = challenge.name,
                    reward = challenge.reward,
                    icon = challenge.icon,
                    progress = challenge.progress,
                    isActive = true
                )
            }
        }
    }
}

@Composable
fun CompletedChallengesContent(darkCardColor: Color) {
    val completedChallenges = remember { getCompletedChallengesSampleData() }

    if (completedChallenges.isEmpty()) {
        EmptyChallengesState(
            message = "No completed challenges yet",
            subMessage = "Complete challenges to see them here"
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(completedChallenges) { challenge ->
                ChallengeCard(
                    darkCardColor = darkCardColor,
                    challengeName = challenge.name,
                    reward = challenge.reward,
                    icon = challenge.icon,
                    progress = 1.0f,
                    isActive = false
                )
            }
        }
    }
}

@Composable
fun BrowseChallengesContent(darkCardColor: Color) {
    val browseChallenges = remember { getBrowseChallengesSampleData() }

    Column {
        // Categories
        ChallengeCategories()

        Spacer(modifier = Modifier.height(24.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(bottom = 24.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(browseChallenges) { challenge ->
                ChallengeCard(
                    darkCardColor = darkCardColor,
                    challengeName = challenge.name,
                    reward = challenge.reward,
                    icon = challenge.icon,
                    progress = null,
                    isActive = false,
                    showJoinButton = true
                )
            }
        }
    }
}

@Composable
fun ChallengeCategories() {
    val categories = listOf("All", "Cardio", "Strength", "Flexibility", "Nutrition")
    val selectedCategory = remember { mutableStateOf("All") }

    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(categories.size) { index ->
            CategoryChip(
                text = categories[index],
                isSelected = selectedCategory.value == categories[index],
                onClick = { selectedCategory.value = categories[index] }
            )
        }
    }
}

@Composable
fun CategoryChip(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(
                if (isSelected) Color(0xFFBF5AF2) else Color(0xFF2C2C2E),
                RoundedCornerShape(20.dp)
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isSelected) Color.White else Color.Gray,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun ChallengeCard(
    darkCardColor: Color,
    challengeName: String,
    reward: String,
    icon: String,
    progress: Float?,
    isActive: Boolean,
    showJoinButton: Boolean = false
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(0.85f),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = darkCardColor
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Icon
            Box(
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFF2C2C2E)),
                contentAlignment = Alignment.Center
            ) {
                when (icon) {
                    "running" -> Icon(
                        Lucide.Footprints,
                        contentDescription = "Running Challenge",
                        tint = Color(0xFFBF5AF2),
                        modifier = Modifier.size(32.dp)
                    )
                    "muscle" -> Icon(
                        Lucide.Dumbbell,
                        contentDescription = "Strength Challenge",
                        tint = Color(0xFFBF5AF2),
                        modifier = Modifier.size(32.dp)
                    )
                    "meditation" -> Icon(
                        Lucide.Flame,
                        contentDescription = "Meditation Challenge",
                        tint = Color(0xFFBF5AF2),
                        modifier = Modifier.size(32.dp)
                    )
                    "water" -> Icon(
                        Lucide.Droplets,
                        contentDescription = "Water Challenge",
                        tint = Color(0xFFBF5AF2),
                        modifier = Modifier.size(32.dp)
                    )
                    "sleep" -> Icon(
                        Lucide.Moon,
                        contentDescription = "Sleep Challenge",
                        tint = Color(0xFFBF5AF2),
                        modifier = Modifier.size(32.dp)
                    )
                    "nutrition" -> Icon(
                        Lucide.Apple,
                        contentDescription = "Nutrition Challenge",
                        tint = Color(0xFFBF5AF2),
                        modifier = Modifier.size(32.dp)
                    )
                    else -> Icon(
                        Lucide.Medal,
                        contentDescription = "Challenge",
                        tint = Color(0xFFBF5AF2),
                        modifier = Modifier.size(32.dp)
                    )
                }
            }

            // Challenge Name
            Text(
                text = challengeName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                maxLines = 2,
            )

            // Reward
            Text(
                text = "$reward XP reward",
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray,
                textAlign = TextAlign.Center
            )

            // Progress or Join Button
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                contentAlignment = Alignment.Center
            ) {
                if (progress != null) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                        ) {
                            // Track
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Color(0xFF3A2A47))
                            )

                            // Progress
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(progress)
                                    .height(6.dp)
                                    .clip(RoundedCornerShape(3.dp))
                                    .background(Color(0xFFBF5AF2))
                            )
                        }

                        Text(
                            text = "${(progress * 100).toInt()}% Complete",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Gray
                        )
                    }
                } else if (showJoinButton) {
                    Button(
                        onClick = { /* Join challenge */ },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFBF5AF2),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(36.dp),
                        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp)
                    ) {
                        Text(
                            text = "Join",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                } else {
                    // For completed challenges, show badge or trophy
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            Lucide.Check,
                            contentDescription = "Completed",
                            tint = Color(0xFFBF5AF2),
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "Completed",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFBF5AF2)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun EmptyChallengesState(message: String, subMessage: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Lucide.Award,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(64.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = message,
            style = MaterialTheme.typography.titleLarge,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = subMessage,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { /* Browse challenges */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xFFBF5AF2),
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(20.dp)
        ) {
            Text("Browse Challenges")
        }
    }
}

data class Challenge(
    val id: String,
    val name: String,
    val reward: String,
    val icon: String,
    val progress: Float? = null
)

fun getActiveChallengesSampleData(): List<Challenge> {
    return listOf(
        Challenge("1", "10K Steps Daily", "100", "running", 0.7f),
        Challenge("2", "30-Day Plank", "250", "muscle", 0.4f),
        Challenge("3", "Morning Meditation", "150", "meditation", 0.2f)
    )
}

fun getCompletedChallengesSampleData(): List<Challenge> {
    return listOf(
        Challenge("4", "Water Challenge", "200", "water"),
        Challenge("5", "Sleep Tracker", "150", "sleep"),
        Challenge("6", "Protein Intake", "300", "nutrition")
    )
}

fun getBrowseChallengesSampleData(): List<Challenge> {
    return listOf(
        Challenge("7", "10K Steps Daily", "100", "running"),
        Challenge("8", "30-Day Plank", "250", "muscle"),
        Challenge("9", "Morning Meditation", "150", "meditation"),
        Challenge("10", "Water Challenge", "200", "water"),
        Challenge("11", "Sleep Tracker", "150", "sleep"),
        Challenge("12", "Protein Intake", "300", "nutrition")
    )
}
