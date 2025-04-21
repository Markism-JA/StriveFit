package com.marky.strivefit.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.marky.strivefit.ui.theme.StriveFitTheme

@Composable
fun AnimatedWelcomeButton(
    onSignUpClick: () -> Unit,
    onGuestClick: () -> Unit,
    animationTriggered: Boolean,
    modifier: Modifier = Modifier
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
                modifier = Modifier.padding(top = 20.dp)
            )

            Box(
                modifier = Modifier
                    .graphicsLayer {
                        alpha = if (buttonsAlpha > 0.5f) ((buttonsAlpha - 0.5f) * 2f) else 0f
                        scaleX = if (buttonsAlpha > 0.5f) 0.8f + ((buttonsAlpha - 0.5f) * 0.4f) else 0.8f
                        scaleY = if (buttonsAlpha > 0.5f) 0.8f + ((buttonsAlpha - 0.5f) * 0.4f) else 0.8f
                    }
            ) {
                AnimatedCustomButton(
                    onClick = onGuestClick,
                    text = "Continue as Guest",
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(top = 10.dp).width(200.dp)
                )
            }
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