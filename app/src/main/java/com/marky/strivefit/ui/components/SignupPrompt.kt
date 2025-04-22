package com.marky.strivefit.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.clickable
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
fun SignupPrompt(
    onSignupClick: () -> Unit,
    animationTriggered: Boolean,
    modifier: Modifier = Modifier
) {
    val loginTextAlpha by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0f,
        animationSpec = tween(durationMillis = 500),
        label = "loginTextAlpha"
    )

    val loginTextOffset by animateFloatAsState(
        targetValue = if (animationTriggered) 0f else 30f,
        animationSpec = tween(durationMillis = 500, easing = EaseOutCirc),
        label = "loginTextOffset"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                alpha = loginTextAlpha
                translationY = loginTextOffset
            }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Don't have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground
            )
            Text(
                text = "Sign Up",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clickable(onClick = onSignupClick),
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignupPromptPreview() {
    StriveFitTheme {
        Surface {
            SignupPrompt(
                onSignupClick = {},
                animationTriggered = true
            )
        }
    }
}