package com.marky.strivefit.ui.screens.userSetup

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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.GoBackButton
import com.marky.strivefit.ui.utilities.calculateWindowHeightSizeClass
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun EquipmentSelectionScreen(
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

    // List of equipment to choose from
    val equipmentList = listOf(
        "No Equipment (Body Weight Only)",
        "Dumbbells",
        "Barbell",
        "Kettlebell",
        "Resistance Bands",
        "Pull-up Bar",
        "Bench",
        "Cable Machine",
        "Stability Ball",
        "Medicine Ball",
        "Smith Machine",
        "Trap Bar",
        "EZ Barbell",
        "Olympic Barbell",
        "Leverage Machine",
        "Sled Machine",
        "Bosu Ball",
        "Wheel Roller",
        "Full Gym Access"
    )

    // Equipment mapping to API values (if needed)
    val equipmentMapping = mapOf(
        "No Equipment (Body Weight Only)" to "body weight",
        "Dumbbells" to "dumbbell",
        "Barbell" to "barbell",
        "Kettlebell" to "kettlebell",
        "Resistance Bands" to "band",
        "Pull-up Bar" to "assisted",
        "Bench" to "weighted",
        "Cable Machine" to "cable",
        "Stability Ball" to "stability ball",
        "Medicine Ball" to "medicine ball",
        "Smith Machine" to "smith machine",
        "Trap Bar" to "trap bar",
        "EZ Barbell" to "ez barbell",
        "Olympic Barbell" to "olympic barbell",
        "Leverage Machine" to "leverage machine",
        "Sled Machine" to "sled machine",
        "Bosu Ball" to "bosu ball",
        "Wheel Roller" to "wheel roller",
        "Full Gym Access" to "" // Special case that might select multiple or all
    )

    // Track selected equipment
    val selectedEquipment = remember { mutableStateListOf<String>() }

    // Function to toggle equipment selection
    fun toggleEquipmentSelection(equipment: String) {
        if (selectedEquipment.contains(equipment)) {
            selectedEquipment.remove(equipment)
        } else {
            // If selecting "No Equipment" or "Full Gym Access", clear other selections
            if (equipment == "No Equipment (Body Weight Only)" || equipment == "Full Gym Access") {
                selectedEquipment.clear()
            } else {
                // If selecting specific equipment, remove "No Equipment" and "Full Gym Access"
                selectedEquipment.remove("No Equipment (Body Weight Only)")
                selectedEquipment.remove("Full Gym Access")
            }
            selectedEquipment.add(equipment)
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
                    EquipmentLandscapeLayout(
                        heightSizeClass = heightSizeClass,
                        onBackClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onBackClick()
                            }
                        },
                        equipmentList = equipmentList,
                        selectedEquipment = selectedEquipment,
                        onContinueClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onContinueClick()
                            }
                        },
                        toggleEquipmentSelection = { equipment -> toggleEquipmentSelection(equipment) },
                        scrollState = scrollState
                    )
                }

                else -> {
                    EquipmentPortraitLayout(
                        heightSizeClass = heightSizeClass,
                        onBackClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onBackClick()
                            }
                        },
                        equipmentList = equipmentList,
                        selectedEquipment = selectedEquipment,
                        onContinueClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onContinueClick()
                            }
                        },
                        toggleEquipmentSelection = { equipment -> toggleEquipmentSelection(equipment) },
                    )
                }
            }
        }
    }
}

@Composable
private fun EquipmentLandscapeLayout(
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    equipmentList: List<String>,
    selectedEquipment: List<String>,
    toggleEquipmentSelection: (String) -> Unit,
    onContinueClick: () -> Unit,
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
                text = "Available Equipment",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 24.dp)
            )
        }
        Spacer(modifier = Modifier.height(verticalSpacing))

        Text(
            text = "Tell us what equipment you have access to",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        LinearProgressIndicator(
            progress = { 0.875f },
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
                text = "Step 7 of 8",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(verticalSpacing))
        // Equipment selection list
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(vertical = 16.dp)
            ) {
                equipmentList.forEach { equipment ->
                    val isSelected = selectedEquipment.contains(equipment)

                    EquipmentCheckbox(
                        text = equipment,
                        checked = isSelected,
                        onCheckedChange = { toggleEquipmentSelection(equipment) }
                    )
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
//                enabled = selectedEquipment.isNotEmpty()
        )
    }
}

@Composable
private fun EquipmentPortraitLayout(
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    equipmentList: List<String>,
    selectedEquipment: List<String>,
    toggleEquipmentSelection: (String) -> Unit,
    onContinueClick: () -> Unit,
) {
    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 16.dp
        WindowHeightSizeClass.Medium -> 12.dp
        else -> 8.dp
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Top section - header
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
                text = "Available Equipment",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(start = 24.dp)
            )
        }

        Text(
            text = "Tell us what equipment you have access to",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp)
        )

        LinearProgressIndicator(
            progress = { 0.875f },
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
                text = "Step 7 of 8",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(verticalSpacing))

        // Scrollable middle section
        Column(
            modifier = Modifier
                .weight(1f) // this makes it take up remaining vertical space
                .verticalScroll(rememberScrollState())
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            equipmentList.forEach { equipment ->
                val isSelected = selectedEquipment.contains(equipment)

                EquipmentCheckbox(
                    text = equipment,
                    checked = isSelected,
                    onCheckedChange = { toggleEquipmentSelection(equipment) }
                )
            }
        }

        Spacer(modifier = Modifier.height(verticalSpacing))

        // Fixed bottom section - Continue button
        AnimatedCustomButton(
            onClick = onContinueClick,
            text = "Continue",
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp)
//            enabled = selectedEquipment.isNotEmpty()
        )
    }
}


@Composable
fun EquipmentCheckbox(
    text: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(vertical = 2.dp)
            .clickable(
                role = Role.Checkbox,
                onClick = { onCheckedChange(!checked) }
            ),
        color = if (checked) MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
        else MaterialTheme.colorScheme.surfaceVariant,
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checked,
                onCheckedChange = null, // Remove direct checkbox interaction
                colors = CheckboxDefaults.colors(
                    checkedColor = MaterialTheme.colorScheme.primary,
                    uncheckedColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )

            Spacer(modifier = Modifier.width(12.dp))

            Text(
                text = text,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewEquipmentSelectionScreen() {
    MaterialTheme {
        EquipmentSelectionScreen()
    }
}

