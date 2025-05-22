package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.GoBackButton
import com.marky.strivefit.ui.utilities.calculateWindowHeightSizeClass
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@Composable
fun WeightInputScreen(
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


    // States for weight values
    var useKilograms by remember { mutableStateOf(true) }
    var kilogramValue by remember { mutableStateOf("") }
    var poundValue by remember { mutableStateOf("") }

    // Track focus states
    var kgFieldFocused by remember { mutableStateOf(false) }
    var lbsFieldFocused by remember { mutableStateOf(false) }


    var isKgFieldFocused by remember { mutableStateOf(false) }
    var onLbsFieldFocused by remember { mutableStateOf(false) }


    // Helper function to convert kg to lbs
    fun kgToLbs(kg: Float) {
        val lbs = (kg * 2.20462f).roundToInt()
        poundValue = lbs.toString()
    }

    // Helper function to convert lbs to kg
    fun lbsToKg(lbs: Float) {
        val kg = (lbs / 2.20462f).roundToInt()
        kilogramValue = kg.toString()
    }

    // Effect to handle conversion when toggle changes
    LaunchedEffect(useKilograms) {
        if (useKilograms) {
            // Convert from lbs to kg
            val lbs = poundValue.toFloatOrNull() ?: 0f
            if (lbs > 0) {
                lbsToKg(lbs)
            }
        } else {
            // Convert from kg to lbs
            val kg = kilogramValue.toFloatOrNull() ?: 0f
            if (kg > 0) {
                kgToLbs(kg)
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
                    WeightLandscapeLayout(
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
                        useKilograms = useKilograms,
                        kilogramValue = kilogramValue,
                        onKilogramValue = { kilogramValue = it },
                        kgToLbs = { kg -> kgToLbs(kg) },
                        poundValue = poundValue,
                        onPoundValue = { poundValue = it },
                        onKgFieldFocused = { isFocused -> isKgFieldFocused = isFocused },
                        lbsToKg = { lbs -> lbsToKg(lbs) },
                        onUseKilograms = { useKilograms = it },
                        onLbsFieldFocused = { isFocused -> onLbsFieldFocused = isFocused },
                        )
                }

                else -> {
                    WeightPortraitLayout(
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
                        useKilograms = useKilograms,
                        kilogramValue = kilogramValue,
                        onKilogramValue = { kilogramValue = it },
                        kgToLbs = { kg -> kgToLbs(kg) },
                        poundValue = poundValue,
                        onPoundValue = { poundValue = it },
                        onKgFieldFocused = { isFocused -> isKgFieldFocused = isFocused },
                        lbsToKg = { lbs -> lbsToKg(lbs) },
                        onUseKilograms = { useKilograms = it },
                        onLbsFieldFocused = { isFocused -> onLbsFieldFocused = isFocused },
                    )
                }
            }
        }
    }
}


@Composable
private fun WeightLandscapeLayout(
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    scrollState: ScrollState,
    useKilograms: Boolean,
    kilogramValue: String,
    onKilogramValue: (String) -> Unit,
    kgToLbs: (Float) -> Unit,
    onPoundValue: (String) -> Unit,
    onKgFieldFocused: (Boolean) -> Unit,
    poundValue: String,
    lbsToKg: (Float) -> Unit,
    onLbsFieldFocused: (Boolean) -> Unit,
    onUseKilograms: (Boolean) -> Unit,

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
                text = "Your Weight",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(
            progress = { 0.375f },
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
                text = "Step 3 of 8",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 4.dp)
            )
        }


        Column(
            verticalArrangement = Arrangement.spacedBy(verticalSpacing),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (useKilograms) {
                // Kilogram input
                OutlinedTextField(
                    value = kilogramValue,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                            onKilogramValue(newValue)

                            // Update pounds when kg changes
                            val kg = newValue.toFloatOrNull() ?: 0f
                            if (kg > 0) {
                                kgToLbs(kg)
                            } else {
                                onPoundValue("")
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = "0",
                            style = MaterialTheme.typography.displayLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    textStyle = MaterialTheme.typography.displayLarge.copy(
                        textAlign = TextAlign.Center,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .width(200.dp)
                        .onFocusChanged { state ->
                            onKgFieldFocused(state.isFocused)
                        }
                )

                Text(
                    text = "kilograms",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            } else {
                // Pounds input
                OutlinedTextField(
                    value = poundValue,
                    onValueChange = { newValue ->
                        if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                            onPoundValue(newValue)

                            // Update kg when pounds changes
                            val lbs = newValue.toFloatOrNull() ?: 0f
                            if (lbs > 0) {
                                lbsToKg(lbs)
                            } else {
                                onKilogramValue("")
                            }
                        }
                    },
                    placeholder = {
                        Text(
                            text = "0",
                            style = MaterialTheme.typography.displayLarge,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth()
                        )
                    },
                    textStyle = MaterialTheme.typography.displayLarge.copy(
                        textAlign = TextAlign.Center,
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number
                    ),
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.Transparent,
                        unfocusedContainerColor = Color.Transparent,
                        disabledContainerColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        cursorColor = MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier
                        .width(200.dp)
                        .onFocusChanged { state ->
                            onLbsFieldFocused(state.isFocused)
                        }
                )

                Text(
                    text = "pounds",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Custom segmented toggle button
            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(48.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(MaterialTheme.colorScheme.surfaceVariant)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize()
                ) {
                    // KG option
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                if (useKilograms) MaterialTheme.colorScheme.primary
                                else Color.Transparent
                            )
                            .clickable { onUseKilograms(true) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "kg",
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (useKilograms)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // LBS option
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clip(RoundedCornerShape(24.dp))
                            .background(
                                if (!useKilograms) MaterialTheme.colorScheme.primary
                                else Color.Transparent
                            )
                            .clickable { onUseKilograms(false) },
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "lbs",
                            style = MaterialTheme.typography.bodyLarge,
                            color = if (!useKilograms)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(verticalSpacing * 2f))
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
private fun WeightPortraitLayout(
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    onContinueClick: () -> Unit,
    scrollState: ScrollState,
    useKilograms: Boolean,
    kilogramValue: String,
    onKilogramValue: (String) -> Unit,
    kgToLbs: (Float) -> Unit,
    onPoundValue: (String) -> Unit,
    onKgFieldFocused: (Boolean) -> Unit,
    poundValue: String,
    lbsToKg: (Float) -> Unit,
    onLbsFieldFocused: (Boolean) -> Unit,
    onUseKilograms: (Boolean) -> Unit,

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
                text = "Your Weight",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        LinearProgressIndicator(
            progress = { 0.375f },
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
                text = "Step 3 of 8",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (useKilograms) {
            // Kilogram input
            OutlinedTextField(
                value = kilogramValue,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                        onKilogramValue(newValue)

                        // Update pounds when kg changes
                        val kg = newValue.toFloatOrNull() ?: 0f
                        if (kg > 0) {
                            kgToLbs(kg)
                        } else {
                            onPoundValue("")
                        }
                    }
                },
                placeholder = {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                textStyle = MaterialTheme.typography.displayLarge.copy(
                    textAlign = TextAlign.Center,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .width(200.dp)
                    .onFocusChanged { state ->
                        onKgFieldFocused(state.isFocused)
                    }
            )

            Text(
                text = "kilograms",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        } else {
            // Pounds input
            OutlinedTextField(
                value = poundValue,
                onValueChange = { newValue ->
                    if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                        onPoundValue(newValue)

                        // Update kg when pounds changes
                        val lbs = newValue.toFloatOrNull() ?: 0f
                        if (lbs > 0) {
                            lbsToKg(lbs)
                        } else {
                            onKilogramValue("")
                        }
                    }
                },
                placeholder = {
                    Text(
                        text = "0",
                        style = MaterialTheme.typography.displayLarge,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                },
                textStyle = MaterialTheme.typography.displayLarge.copy(
                    textAlign = TextAlign.Center,
                ),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                ),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = MaterialTheme.colorScheme.primary
                ),
                modifier = Modifier
                    .width(200.dp)
                    .onFocusChanged { state ->
                        onLbsFieldFocused(state.isFocused)
                    }
            )

            Text(
                text = "pounds",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Custom segmented toggle button
        Box(
            modifier = Modifier
                .width(240.dp)
                .height(48.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Row(
                modifier = Modifier.fillMaxSize()
            ) {
                // KG option
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            if (useKilograms) MaterialTheme.colorScheme.primary
                            else Color.Transparent
                        )
                        .clickable { onUseKilograms(true) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "kg",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (useKilograms)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // LBS option
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(24.dp))
                        .background(
                            if (!useKilograms) MaterialTheme.colorScheme.primary
                            else Color.Transparent
                        )
                        .clickable { onUseKilograms(false) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "lbs",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (!useKilograms)
                            MaterialTheme.colorScheme.onPrimary
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }

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
fun PreviewWeightInputScreen() {
    MaterialTheme {
        WeightInputScreen()
    }
}