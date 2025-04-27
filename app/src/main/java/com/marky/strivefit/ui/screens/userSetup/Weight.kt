package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.GoBackButton
import kotlin.math.roundToInt

@Composable
fun WeightInputScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    // States for weight values
    var useKilograms by remember { mutableStateOf(true) }
    var kilogramValue by remember { mutableStateOf("") }
    var poundValue by remember { mutableStateOf("") }

    // Track focus states
    var kgFieldFocused by remember { mutableStateOf(false) }
    var lbsFieldFocused by remember { mutableStateOf(false) }

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
                                kilogramValue = newValue

                                // Update pounds when kg changes
                                val kg = newValue.toFloatOrNull() ?: 0f
                                if (kg > 0) {
                                    kgToLbs(kg)
                                } else {
                                    poundValue = ""
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
                                kgFieldFocused = state.isFocused
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
                                poundValue = newValue

                                // Update kg when pounds changes
                                val lbs = newValue.toFloatOrNull() ?: 0f
                                if (lbs > 0) {
                                    lbsToKg(lbs)
                                } else {
                                    kilogramValue = ""
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
                                lbsFieldFocused = state.isFocused
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
                                .clickable { useKilograms = true },
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
                                .clickable { useKilograms = false },
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
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWeightInputScreen() {
    MaterialTheme {
        WeightInputScreen()
    }
}