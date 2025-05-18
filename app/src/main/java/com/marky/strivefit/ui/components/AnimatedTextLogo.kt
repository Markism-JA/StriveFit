package com.marky.strivefit.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Surface
import com.marky.strivefit.ui.theme.StriveFitTheme

@Composable
fun AnimatedTextLogo(
    isAnimationPlayed: Boolean,
    animationTriggered: Boolean,
    modifier: Modifier = Modifier
) {
    val textLogoAlpha by animateFloatAsState(
        targetValue = if (animationTriggered) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = "textLogoAlpha"
    )

    val textLogoY by animateFloatAsState(
        targetValue = if (animationTriggered) 0f else 30f,
        animationSpec = tween(
            durationMillis = 1000,
            easing = EaseOutCirc
        ),
        label = "textLogoY"
    )

    val floatAnimation = rememberInfiniteTransition(label = "floatAnimation")
    val floatOffset by floatAnimation.animateFloat(
        initialValue = 0f,
        targetValue = 5f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = EaseInOutSine),
            repeatMode = RepeatMode.Reverse
        ),
        label = "floatOffset"
    )

    Box(
        modifier = modifier
            .graphicsLayer {
                alpha = textLogoAlpha
                translationY = textLogoY + (if (isAnimationPlayed) floatOffset else 0f)
            }
    ) {
        TextLogoIcon(Modifier.width(250.dp).padding(top = 20.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun AnimatedTextLogoPreview() {
    StriveFitTheme {
        Surface {
            AnimatedTextLogo(
                isAnimationPlayed = true,
                animationTriggered = true
            )
        }
    }
}