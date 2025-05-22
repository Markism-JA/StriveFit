package com.marky.strivefit.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.animation.createButtonAnimations

@Composable
fun AnimatedCustomButton(
    onClick: () -> Unit,
    text: String,
    backgroundColor: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()

    // Use the function to handle the animations inside the composable context
    val (scale, elevationAnimation) = createButtonAnimations(isPressed)

    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = backgroundColor,
            contentColor = textColor
        ),
        shape = MaterialTheme.shapes.medium,
        interactionSource = interactionSource,
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = elevationAnimation.value.dp,
            pressedElevation = elevationAnimation.value.dp
        ),
        modifier = modifier
            .scale(scale)
            .height(48.dp)
            .defaultMinSize(minWidth = 200.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}

