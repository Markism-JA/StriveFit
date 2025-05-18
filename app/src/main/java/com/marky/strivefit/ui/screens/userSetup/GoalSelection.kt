package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.GoBackButton

@Composable
fun GoalsInputScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    // List of available goals
    val goalOptions = listOf(
        "Build muscle",
        "Lose weight",
        "Improve fitness",
        "Increase strength",
        "Improve flexibility",
        "Train for sports",
        "Maintain current fitness"
    )

    // Track selected goals (up to 3)
    val selectedGoals = remember { mutableStateListOf<String>() }

    // Function to toggle goal selection
    fun toggleGoal(goal: String) {
        if (selectedGoals.contains(goal)) {
            // If already selected, deselect it
            selectedGoals.remove(goal)
        } else {
            // If not selected and less than 3 goals are selected, add it
            if (selectedGoals.size < 3) {
                selectedGoals.add(goal)
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
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
                        text = "Your Goals",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Text(
                    text = "Select your primary fitness goals (choose up to 3)",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                )

                LinearProgressIndicator(
                    progress = { 0.5f },
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
                        text = "Step 4 of 8",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            // Goals selection list
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                goalOptions.forEach { goal ->
                    val isSelected = selectedGoals.contains(goal)

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .clickable { toggleGoal(goal) },
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .background(
                                    if (isSelected) MaterialTheme.colorScheme.primary
                                    else Color.Transparent
                                )
                                .border(
                                    width = 2.dp,
                                    color = if (isSelected) MaterialTheme.colorScheme.primary
                                    else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )

                        Text(
                            text = goal,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }

            // Continue button
            AnimatedCustomButton(
                onClick = onContinueClick,
                text = "Continue",
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGoalsInputScreen() {
    MaterialTheme {
        GoalsInputScreen()
    }
}