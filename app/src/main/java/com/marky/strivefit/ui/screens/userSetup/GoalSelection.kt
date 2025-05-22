package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.GoBackButton
import com.marky.strivefit.ui.utilities.calculateWindowHeightSizeClass
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GoalsInputScreen(
    windowSizeClass: WindowSizeClass? = null,
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val widthSizeClass by remember(windowSizeClass, screenWidth) {
        mutableStateOf(
            windowSizeClass?.widthSizeClass ?: calculateWindowWidthSizeClass(screenWidth)
        )
    }

    val heightSizeClass by remember(windowSizeClass, screenHeight) {
        mutableStateOf(
            windowSizeClass?.heightSizeClass ?: calculateWindowHeightSizeClass(
                screenHeight
            )
        )
    }

    val aspectRatio = screenWidth / screenHeight
    val isLandscape = aspectRatio > 1.2f && widthSizeClass != WindowWidthSizeClass.Compact

    val paddingStart = when (isLandscape) {
        true -> 18.dp
        false -> 5.dp
    }
    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }

    val scrollState = rememberScrollState()


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
            when {
                isLandscape -> {
                    GoalLandscapeLayout(
                        heightSizeClass = heightSizeClass,
                        onBackClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onBackClick()
                            }
                        },
                        onContinueClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onContinueClick()
                            }
                        },
                        scrollState = scrollState,
                        goalOptions = goalOptions,
                        selectedGoals = selectedGoals,
                        toggleGoal = { goal -> toggleGoal(goal) },

                        )
                }

                else -> {
                    GoalPortraitLayout(
                        heightSizeClass = heightSizeClass,
                        onBackClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onBackClick()
                            }
                        },
                        onContinueClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onContinueClick()
                            }
                        },
                        scrollState = scrollState,
                        goalOptions = goalOptions,
                        selectedGoals = selectedGoals,
                        toggleGoal = { goal -> toggleGoal(goal) },
                    )
                }
            }
        }
    }
}



@Composable
private fun GoalLandscapeLayout(
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    scrollState: ScrollState,
    goalOptions: List<String>,
    selectedGoals: List<String>,
    toggleGoal: (String) -> Unit,

    ) {
    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 12.dp
        WindowHeightSizeClass.Medium -> 8.dp
        else -> 6.dp
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 80.dp)
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalSpacing)
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
        Spacer(modifier = Modifier.height(verticalSpacing))
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

        // Goals selection list

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)

            ) {
                goalOptions.forEachIndexed { index, goal ->
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
                    if (index < goalOptions.lastIndex) {
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }

            }
        }
        Spacer(modifier = Modifier.height(verticalSpacing))
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






@Composable
private fun GoalPortraitLayout(
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    scrollState: ScrollState,
    goalOptions: List<String>,
    selectedGoals: List<String>,
    toggleGoal: (String) -> Unit,

) {
    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 16.dp
        WindowHeightSizeClass.Medium -> 12.dp
        else -> 8.dp
    }
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


        // Goals selection list

        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
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











@Preview(showBackground = true)
@Composable
fun PreviewGoalsInputScreen() {
    MaterialTheme {
        GoalsInputScreen()
    }
}
