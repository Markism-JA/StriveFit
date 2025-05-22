package com.marky.strivefit.ui.components.animated

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.theme.StriveFitTheme

@Composable
fun AnimatedWelcomeButton(
    onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit,
    animationTriggered: Boolean,
    modifier: Modifier = Modifier,
    widthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.Medium
) {
    val buttonsAlpha by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0f,
        animationSpec = tween(durationMillis = 800),
        label = "buttonsAlpha"
    )

    val buttonsOffset by animateFloatAsState(
        targetValue = if (animationTriggered) 0f else 40f,
        animationSpec = tween(durationMillis = 800, easing = EaseOutCirc),
        label = "buttonsOffset"
    )

    // Adjust button width based on screen size
    val buttonWidth = when (widthSizeClass) {
        WindowWidthSizeClass.Expanded -> 280.dp
        WindowWidthSizeClass.Medium -> 240.dp
        else -> 200.dp
    }

    Box(
        modifier = modifier
            .graphicsLayer {
                alpha = buttonsAlpha
                translationY = buttonsOffset
            }
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedCustomButton(
                onClick = onSignUpClick,
                text = "Sign Up",
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .width(buttonWidth)
            )


                AnimatedCustomButton(
                    onClick = onGuestClick,
                    text = "Continue as Guest",
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .width(buttonWidth)
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedButtonGroupPreview() {
    StriveFitTheme {
        Surface {
            AnimatedWelcomeButton(
                onSignUpClick = {},
                onGuestClick = {},
                animationTriggered = true
            )
        }
    }
}