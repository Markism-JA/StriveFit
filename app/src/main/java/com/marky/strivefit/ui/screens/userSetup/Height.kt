package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.GoBackButton
import kotlin.math.floor
import kotlin.math.roundToInt

@Composable
fun HeightInputScreen(
    onBackClick: () -> Unit = {},
    onContinueClick: () -> Unit = {}
) {
    // States for height values - using empty string as default
    var useCentimeters by remember { mutableStateOf(true) }
    var centimeterValue by remember { mutableStateOf("") }
    var feetValue by remember { mutableStateOf("") }
    var inchesValue by remember { mutableStateOf("") }

    // Helper function to convert cm to feet and inches
    fun cmToFeetInches(cm: Float) {
        val totalInches = cm * 0.393701f
        val feet = floor(totalInches / 12).toInt()
        val inches = (totalInches % 12).roundToInt()

        // Handle case where inches equals 12
        if (inches == 12) {
            feetValue = (feet + 1).toString()
            inchesValue = "0"
        } else {
            feetValue = feet.toString()
            inchesValue = inches.toString()
        }
    }

    // Helper function to convert feet and inches to cm
    fun feetInchesToCm(feet: Int, inches: Int) {
        val totalInches = feet * 12 + inches
        val cm = (totalInches * 2.54f).roundToInt()
        centimeterValue = cm.toString()
    }

    // Effect to handle conversion when toggle changes
    LaunchedEffect(useCentimeters) {
        if (useCentimeters) {
            // Convert from feet & inches to cm
            val feet = feetValue.toIntOrNull() ?: 0
            val inches = inchesValue.toIntOrNull() ?: 0
            if (feet > 0 || inches > 0) {
                feetInchesToCm(feet, inches)
            }
        } else {
            // Convert from cm to feet & inches
            val cm = centimeterValue.toFloatOrNull() ?: 0f
            if (cm > 0) {
                cmToFeetInches(cm)
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
                        text = "Your Height",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                LinearProgressIndicator(
                    progress = { 0.25f },
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
                        text = "Step 2 of 8",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (useCentimeters) {
                    // Centimeter input with placeholder
                    OutlinedTextField(
                        value = centimeterValue,
                        onValueChange = { newValue ->
                            if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                centimeterValue = newValue

                                // Update feet and inches when cm changes
                                val cm = newValue.toFloatOrNull() ?: 0f
                                if (cm > 0) {
                                    cmToFeetInches(cm)
                                } else {
                                    feetValue = ""
                                    inchesValue = ""
                                }
                            }
                        },
                        textStyle = MaterialTheme.typography.displayLarge.copy(
                            textAlign = TextAlign.Center,
                        ),
                        placeholder = {
                            Text(
                                text = "0",
                                style = MaterialTheme.typography.displayLarge.copy(
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                ),
                                modifier = Modifier.fillMaxWidth()
                            )
                        },
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
                        modifier = Modifier.width(200.dp)
                    )
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        // Feet input with placeholder
                        OutlinedTextField(
                            value = feetValue,
                            onValueChange = { newValue ->
                                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                    feetValue = newValue

                                    // Update cm when feet changes
                                    val feet = newValue.toIntOrNull() ?: 0
                                    val inches = inchesValue.toIntOrNull() ?: 0
                                    feetInchesToCm(feet, inches)
                                }
                            },
                            textStyle = MaterialTheme.typography.displayLarge.copy(
                                textAlign = TextAlign.Center,
                            ),
                            placeholder = {
                                Text(
                                    text = "0",
                                    style = MaterialTheme.typography.displayLarge.copy(
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
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
                            modifier = Modifier.width(100.dp)
                        )

                        Text(
                            text = "ft",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )

                        // Inches input with placeholder
                        OutlinedTextField(
                            value = inchesValue,
                            onValueChange = { newValue ->
                                if (newValue.isEmpty() || newValue.all { it.isDigit() }) {
                                    inchesValue = newValue

                                    // Update cm when inches changes
                                    val feet = feetValue.toIntOrNull() ?: 0
                                    val inches = newValue.toIntOrNull() ?: 0
                                    feetInchesToCm(feet, inches)
                                }
                            },
                            textStyle = MaterialTheme.typography.displayLarge.copy(
                                textAlign = TextAlign.Center,
                            ),
                            placeholder = {
                                Text(
                                    text = "0",
                                    style = MaterialTheme.typography.displayLarge.copy(
                                        textAlign = TextAlign.Center,
                                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f)
                                    ),
                                    modifier = Modifier.fillMaxWidth()
                                )
                            },
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
                            modifier = Modifier.width(100.dp)
                        )

                        Text(
                            text = "in",
                            style = MaterialTheme.typography.titleLarge,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
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
                        // CM option
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(24.dp))
                                .background(
                                    if (useCentimeters) MaterialTheme.colorScheme.primary
                                    else Color.Transparent
                                )
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { useCentimeters = true },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "cm",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (useCentimeters)
                                    MaterialTheme.colorScheme.onPrimary
                                else
                                    MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }

                        // Ft & In option
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .clip(RoundedCornerShape(24.dp))
                                .background(
                                    if (!useCentimeters) MaterialTheme.colorScheme.primary
                                    else Color.Transparent
                                )
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { useCentimeters = false },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "ft & in",
                                style = MaterialTheme.typography.bodyLarge,
                                color = if (!useCentimeters)
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
fun PreviewHeightInputScreen() {
    MaterialTheme {
        HeightInputScreen()
    }
}