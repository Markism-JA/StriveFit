package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
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
fun ExperienceLevelScreen(
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


    // List of experience levels with descriptions
    val experienceLevels = listOf(
        "Beginner" to "New to working out",
        "Intermediate" to "Some experience",
        "Advanced" to "Consistent training",
        "Expert" to "Years of training"
    )

    // State to track selected experience level
    var selectedLevel by remember { mutableStateOf("") }

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
                    ExperienceLandscapeLayout(
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
                        selectedLevel = selectedLevel,
                        experienceLevels = experienceLevels,
                        onLevelSelected = { selectedLevel = it },

                    )
                }

                else -> {
                    ExperiencePortraitLayout(
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
                        selectedLevel = selectedLevel,
                        experienceLevels = experienceLevels,
                        onLevelSelected = { selectedLevel = it },
                    )
                }
            }
        }
    }
}

    @Composable
    private fun ExperienceLandscapeLayout(
        heightSizeClass: WindowHeightSizeClass,
        onBackClick: () -> Unit,
        onContinueClick: () -> Unit,
        scrollState: ScrollState,
        experienceLevels: List<Pair<String, String>>,
        selectedLevel: String,
        onLevelSelected: (String) -> Unit
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
                    text = "Experience Level",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(verticalSpacing))
            Text(
                text = "Tell us about your fitness experience",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
            )

            LinearProgressIndicator(
                progress = { 0.625f },
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
                    text = "Step 5 of 8",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(verticalSpacing))

            // Experience level selection
            Row(
                verticalAlignment = Alignment.CenterVertically,

            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)

                ) {
                    experienceLevels.forEachIndexed { index, (level, description) ->
                        val isSelected = selectedLevel == level

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onLevelSelected(level) },
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (isSelected)
                                    MaterialTheme.colorScheme.onSecondaryContainer
                                else
                                    MaterialTheme.colorScheme.surfaceVariant
                            ),
                            border = if (isSelected)
                                BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                            else null
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                // Visual indicator for selection
                                RadioButton(
                                    selected = isSelected,
                                    onClick = null,
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.primary,
                                        unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                )

                                // Level information
                                Column(
                                    modifier = Modifier
                                        .padding(start = 8.dp)
                                        .weight(1f)
                                ) {
                                    Text(
                                        text = level,
                                        style = MaterialTheme.typography.titleMedium,
                                        color = if (isSelected)
                                            MaterialTheme.colorScheme.onPrimaryContainer
                                        else
                                            MaterialTheme.colorScheme.onSurface
                                    )

                                    Text(
                                        text = description,
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = if (isSelected)
                                            MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                        else
                                            MaterialTheme.colorScheme.onSurfaceVariant
                                    )
                                }
                            }
                        }
                        if (index < experienceLevels.lastIndex) {
                            Spacer(modifier = Modifier.height(12.dp))
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
                modifier = Modifier.fillMaxWidth(),
//                enabled = selectedLevel.isNotEmpty()
            )
        }
    }



@Composable
private fun ExperiencePortraitLayout(
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    scrollState: ScrollState,
    experienceLevels: List<Pair<String, String>>,
    selectedLevel: String,
    onLevelSelected: (String) -> Unit
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
                text = "Experience Level",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Text(
            text = "Tell us about your fitness experience",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        LinearProgressIndicator(
            progress = { 0.625f },
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
                text = "Step 5 of 8",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 4.dp)
            )
        }


        // Experience level selection
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                experienceLevels.forEach { (level, description) ->
                    val isSelected = selectedLevel == level

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onLevelSelected(level) },
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = if (isSelected)
                                MaterialTheme.colorScheme.onSecondaryContainer
                            else
                                MaterialTheme.colorScheme.surfaceVariant
                        ),
                        border = if (isSelected)
                            BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
                        else null
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Visual indicator for selection
                            RadioButton(
                                selected = isSelected,
                                onClick = null,
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = MaterialTheme.colorScheme.primary,
                                    unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            )

                            // Level information
                            Column(
                                modifier = Modifier
                                    .padding(start = 8.dp)
                                    .weight(1f)
                            ) {
                                Text(
                                    text = level,
                                    style = MaterialTheme.typography.titleMedium,
                                    color = if (isSelected)
                                        MaterialTheme.colorScheme.onPrimaryContainer
                                    else
                                        MaterialTheme.colorScheme.onSurface
                                )

                                Text(
                                    text = description,
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = if (isSelected)
                                        MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f)
                                    else
                                        MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
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
            modifier = Modifier.fillMaxWidth(),
//                enabled = selectedLevel.isNotEmpty()
        )
    }




@Preview(showBackground = true)
@Composable
fun PreviewExperienceLevelScreen() {
    MaterialTheme {
        ExperienceLevelScreen()
    }
}