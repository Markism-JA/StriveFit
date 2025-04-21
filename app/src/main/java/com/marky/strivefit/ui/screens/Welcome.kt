package com.marky.strivefit.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.*
import com.marky.strivefit.ui.modifiers.ScreenAlignments
import com.marky.strivefit.ui.modifiers.ScreenModifiers
import com.marky.strivefit.ui.screens.auth.SignUp
import com.marky.strivefit.ui.theme.StriveFitTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Welcome(
    onSignUpClick: () -> Unit = {},
    onGuestClick: () -> Unit = {},
    onLoginClick: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    var animationPlayed by remember { mutableStateOf(false) }

    val animationSequence = remember { mutableStateListOf<Boolean>().apply {
        repeat(4) { add(false) }
    }}

    LaunchedEffect(key1 = true) {
        animationSequence[0] = true
        delay(300)
        animationSequence[1] = true
        delay(300)
        animationSequence[2] = true
        delay(300)
        animationSequence[3] = true
        animationPlayed = true
    }

    Column(
        modifier = Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background),
        horizontalAlignment = ScreenAlignments.centerColumn.first,
        verticalArrangement = ScreenAlignments.centerColumn.second,
    ) {
        AnimatedLogo(
            isAnimationPlayed = animationPlayed,
            animationTriggered = animationSequence[0]
        )

        AnimatedTextLogo(
            isAnimationPlayed = animationPlayed,
            animationTriggered = animationSequence[1]
        )

        AnimatedWelcomeButton(
            onSignUpClick = {
                coroutineScope.launch {
                    onSignUpClick()
                }
            },
            onGuestClick = onGuestClick,
            animationTriggered = animationSequence[2],
            modifier = Modifier.padding(top = 80.dp)
        )

        LoginPrompt(
            onLoginClick = {
                coroutineScope.launch {
                    onLoginClick()
                }
            },
            animationTriggered = animationSequence[3],
            modifier = Modifier.padding(top = 24.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun WelcomePreview() {
    StriveFitTheme {
        Surface {
            Welcome()
        }
    }
}