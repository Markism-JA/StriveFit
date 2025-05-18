package com.marky.strivefit.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import com.marky.strivefit.ui.theme.StriveFitTheme

@Composable
fun AnimatedLogo(
    isAnimationPlayed: Boolean,
    animationTriggered: Boolean,
    modifier: Modifier = Modifier
) {
    val logoEntryScale = animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0.2f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMediumLow
        ),
        label = "logoEntryScale"
    )

    val logoRotation = animateFloatAsState(
        targetValue = if (animationTriggered) 0f else -30f,
        animationSpec = tween(durationMillis = 800, easing = EaseOutBack),
        label = "logoRotation"
    )

    val pulsateAnimation = rememberInfiniteTransition(label = "pulsateAnimation")
    val pulsateScale by pulsateAnimation.animateFloat(
        initialValue = 1f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1800, easing = EaseInOutQuad),
            repeatMode = RepeatMode.Reverse
        ),
        label = "pulsateScale"
    )

    Box(
        modifier = modifier
            .scale(logoEntryScale.value)
            .graphicsLayer {
                scaleX = if (isAnimationPlayed) pulsateScale else 1f
                scaleY = if (isAnimationPlayed) pulsateScale else 1f
                rotationZ = logoRotation.value
                transformOrigin = TransformOrigin(0.5f, 0.5f)
            }
    ) {
        LogoIcon(Modifier.size(200.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedLogoPreview() {
    StriveFitTheme {
        Surface {
            AnimatedLogo(
                isAnimationPlayed = true,
                animationTriggered = true
            )
        }
    }
}
