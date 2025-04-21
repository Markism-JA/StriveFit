package com.marky.strivefit.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.theme.DefaultDark
import com.marky.strivefit.ui.theme.CommonColors
import com.marky.strivefit.ui.theme.StriveFitTheme

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

    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "buttonScale"
    )

    val elevationAnimation = remember { Animatable(0f) }

    LaunchedEffect(isPressed) {
        elevationAnimation.animateTo(
            targetValue = if (isPressed) 4f else 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

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

@Preview(showBackground = true)
@Composable
fun AnimatedCustomButtonPreview() {
    StriveFitTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnimatedCustomButton(
                    onClick = {},
                    text = "Sign Up",
                    backgroundColor = DefaultDark.primary,
                    textColor = CommonColors.onPrimary
                )

                AnimatedCustomButton(
                    onClick = {},
                    text = "Continue as Guest",
                    backgroundColor = DefaultDark.surfaceElevated,
                    textColor = CommonColors.onPrimary,
                    modifier = Modifier.width(200.dp)
                )
            }
        }
    }
}