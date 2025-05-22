package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.GoBackButton
import com.marky.strivefit.ui.utilities.calculateWindowHeightSizeClass
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun BodyFocusAreasScreen(
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

    // List of body areas to choose from
    val bodyAreas = listOf(
        "Arms",
        "Chest",
        "Back",
        "Shoulders",
        "Core",
        "Legs",
        "Glutes",
        "Full Body"
    )

    // Track selected body areas
    val selectedAreas = remember { mutableStateListOf<String>() }

    // Function to toggle area selection
    fun toggleAreaSelection(area: String) {
        if (selectedAreas.contains(area)) {
            selectedAreas.remove(area)
        } else {
            selectedAreas.add(area)
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
                    BodySelectionLandscapeLayout(
                        heightSizeClass = heightSizeClass,
                        onBackClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onBackClick()
                            }
                        },
                        bodyAreas = bodyAreas,
                        selectedAreas = selectedAreas,
                        onContinueClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onContinueClick()
                            }
                        },
                        toggleAreaSelection = { area -> toggleAreaSelection(area) },
                        scrollState = scrollState,

                    )
                }

                else -> {
                    BodySelectionPortraitLayout(
                        heightSizeClass = heightSizeClass,
                        onBackClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onBackClick()
                            }
                        },
                        bodyAreas = bodyAreas,
                        selectedAreas = selectedAreas,
                        onContinueClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onContinueClick()
                            }
                        },
                        toggleAreaSelection = { area -> toggleAreaSelection(area) }

                    )
                }
            }
        }

        }
    }



    @Composable
    private fun BodySelectionLandscapeLayout(
        heightSizeClass: WindowHeightSizeClass,
        onBackClick: () -> Unit,
        bodyAreas: List<String>,
        selectedAreas: List<String>,
        onContinueClick: () -> Unit,
        toggleAreaSelection: (String) -> Unit,
        scrollState: ScrollState
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
                            text = "Body Focus Areas",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                Spacer(modifier = Modifier.height(verticalSpacing))

                    Text(
                        text = "Select areas you want to focus on",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                    )

                    LinearProgressIndicator(
                        progress = { 0.75f },
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
                            text = "Step 6 of 8",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }


                Spacer(modifier = Modifier.height(verticalSpacing))

                // Body areas selection grid - using simple Columns and Rows
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()
                            .padding(vertical = 16.dp)
                    ) {
                        // Split the list into chunks of 3 for our rows
                        bodyAreas.chunked(3).forEach { rowItems ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 16.dp),
                                horizontalArrangement = Arrangement.spacedBy(
                                    12.dp,
                                    Alignment.CenterHorizontally
                                )
                            ) {
                                rowItems.forEach { area ->
                                    val isSelected = selectedAreas.contains(area)
                                    BodyAreaChip(
                                        text = area,
                                        selected = isSelected,
                                        onSelectedChange = { toggleAreaSelection(area) }
                                    )
                                }
                            }
                        }
                    }

                }

                Spacer(modifier = Modifier.height(verticalSpacing * 1.5f))
                // Continue button
                AnimatedCustomButton(
                    onClick = onContinueClick,
                    text = "Continue",
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.fillMaxWidth(),
//                enabled = selectedAreas.isNotEmpty()
                )
            }
    }


    @Composable
    private fun BodySelectionPortraitLayout(
        heightSizeClass: WindowHeightSizeClass,
        onBackClick: () -> Unit,
        bodyAreas: List<String>,
        selectedAreas: List<String>,
        onContinueClick: () -> Unit,
        toggleAreaSelection: (String) -> Unit,
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
                        text = "Body Focus Areas",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Text(
                    text = "Select areas you want to focus on",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
                )

                LinearProgressIndicator(
                    progress = { 0.75f },
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
                        text = "Step 6 of 8",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(verticalSpacing))

            // Body areas selection grid - using simple Columns and Rows
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Split the list into chunks of 3 for our rows
                bodyAreas.chunked(3).forEach { rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(
                            12.dp,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        rowItems.forEach { area ->
                            val isSelected = selectedAreas.contains(area)
                            BodyAreaChip(
                                text = area,
                                selected = isSelected,
                                onSelectedChange = { toggleAreaSelection(area) }
                            )
                        }
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
            modifier = Modifier.fillMaxWidth(),
//                enabled = selectedAreas.isNotEmpty()
        )
    }


@Composable
fun BodyAreaChip(
    text: String,
    selected: Boolean,
    onSelectedChange: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = onSelectedChange,
        label = {
            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(horizontal = 2.dp)
            )
        },
        modifier = Modifier.height(44.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            labelColor = MaterialTheme.colorScheme.onSurface
        ),
        shape = RoundedCornerShape(22.dp),
        border = null
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBodyFocusAreasScreen() {
    MaterialTheme {
        BodyFocusAreasScreen()
    }
}