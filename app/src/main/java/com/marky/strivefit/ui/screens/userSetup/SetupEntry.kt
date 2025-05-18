package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Entry(isSignedIn: Boolean = false, onContinueClick: (String) -> Unit) {
    val welcomeState = remember { MutableTransitionState(true) }
    val nameInputState = remember { MutableTransitionState(false) }
    val nameInput = remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(Unit) {
        delay(4000)
        welcomeState.targetState = false
        delay(1000)
        nameInputState.targetState = true
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visibleState = welcomeState,
            enter = fadeIn(animationSpec = tween(1000)),
            exit = fadeOut(animationSpec = tween(1000))
        ) {
            WelcomeMessage(isSignedIn)
        }

        AnimatedVisibility(
            visibleState = nameInputState,
            enter = fadeIn(animationSpec = tween(1200)) +
                    slideInVertically(
                        initialOffsetY = { it / 4 },
                        animationSpec = tween(1500, easing = EaseInOutQuad)
                    )
        ) {
            NameInputSection(
                nameInput.value,
                { nameInput.value = it },
                { onContinueClick(nameInput.value.text) }
            )
        }
    }
}

@Composable
fun WelcomeMessage(isSignedIn: Boolean) {
    val textScale = remember { Animatable(0.9f) }
    val textOpacity = remember { Animatable(0f) }
    val subtextOpacity = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        textOpacity.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = EaseOutQuad)
        )
        subtextOpacity.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, delayMillis = 500, easing = EaseOutQuad)
        )
    }

    val subtextMessage = if (isSignedIn) {
        "Finish signing up"
    } else {
        "Entering as guest"
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Hi! Let's have you set up",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .scale(textScale.value)
                    .alpha(textOpacity.value)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = subtextMessage,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
                    .alpha(subtextOpacity.value)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NameInputSection(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onContinueClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .navigationBarsPadding()
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(24.dp))

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier
                .padding(24.dp)
                .clip(MaterialTheme.shapes.large)
                .background(MaterialTheme.colorScheme.background)
                .padding(24.dp)
                .weight(1f, fill = false)
        ) {
            Text(
                text = "What should we call you?",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                textStyle = MaterialTheme.typography.bodyLarge,
                label = { Text("Nickname") },
                singleLine = true,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.primary,
                    focusedLabelColor = MaterialTheme.colorScheme.primary
                )
            )

            AnimatedCustomButton(
                onClick = onContinueClick,
                text = "Continue",
                backgroundColor = MaterialTheme.colorScheme.primary,
                textColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
