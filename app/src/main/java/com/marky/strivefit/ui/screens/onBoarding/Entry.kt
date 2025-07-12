package com.marky.strivefit.ui.screens.onBoarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.EaseOutQuad
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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

@Composable
fun Entry(
    entryType: EntryType,
    userName: String? = null,
    onContinueClick: (String) -> Unit
) {
    val welcomeState = remember { MutableTransitionState(true) }

    val nameInputState = remember { MutableTransitionState(false) }
    var nameInput by remember { mutableStateOf(TextFieldValue()) }

    LaunchedEffect(entryType, userName) {
        when (entryType) {
            EntryType.GUEST -> {
                delay(4000)
                welcomeState.targetState = false
                delay(1000)
                nameInputState.targetState = true
            }
            EntryType.SIGNUP, EntryType.LOGIN -> {
                requireNotNull(userName) { "User name must be provided for SIGNUP or LOGIN entry types." }
                delay(3500)
                onContinueClick(userName)
            }
        }
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
            WelcomeMessage(entryType = entryType, userName = userName)
        }

        if (entryType == EntryType.GUEST) {
            AnimatedVisibility(
                visibleState = nameInputState,
                enter = fadeIn(animationSpec = tween(1200)) +
                        slideInVertically(
                            initialOffsetY = { it / 4 },
                            animationSpec = tween(1500, easing = EaseInOutQuad)
                        )
            ) {
                NameInputSection(
                    value = nameInput,
                    onValueChange = { nameInput = it },
                    onContinueClick = { onContinueClick(nameInput.text) }
                )
            }
        }
    }
}

@Composable
private fun WelcomeMessage(entryType: EntryType, userName: String?) {
    val textScale = remember { Animatable(0.9f) }
    val textOpacity = remember { Animatable(0f) }
    val subtextOpacity = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        textOpacity.animateTo(1f, animationSpec = tween(1000, easing = EaseOutQuad))
        subtextOpacity.animateTo(1f, animationSpec = tween(1000, 500, EaseOutQuad))
    }

    val (welcomeText, subtextMessage) = remember(entryType, userName) {
        when (entryType) {
            EntryType.SIGNUP -> "Welcome, ${userName}!" to "Thanks for signing up."
            EntryType.LOGIN -> "Welcome back, ${userName}!" to "Let's get right to it."
            EntryType.GUEST -> "Hi! Let's get you set up" to "Entering as a guest."
        }
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
                text = welcomeText,
                style = MaterialTheme.typography.headlineLarge,
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
private fun NameInputSection(
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