package com.marky.strivefit.ui.screens.userSetup

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

/**
 * Represents the different states in the loading sequence
 */
enum class LoadingState(val message: String) {
    DOWNLOADING("Downloading exercise content"),
    ANALYZING("Analyzing user profile"),
    GENERATING("Generating Workout Plans"),
    COMPLETE("Everything is ready!")
}
@Composable
fun LoadingScreen(
    onComplete: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    var currentState by remember { mutableStateOf(LoadingState.DOWNLOADING) }
    var showComplete by remember { mutableStateOf(false) }
    val progress = remember { Animatable(0f) }

    // Control the loading sequence animation
    LaunchedEffect(key1 = true) {
        // Phase 1: Downloading
        progress.animateTo(
            targetValue = 0.33f,
            animationSpec = tween(2000, easing = LinearEasing)
        )
        currentState = LoadingState.ANALYZING

        // Phase 2: Analyzing
        progress.animateTo(
            targetValue = 0.66f,
            animationSpec = tween(2000, easing = LinearEasing)
        )
        currentState = LoadingState.GENERATING

        // Phase 3: Generating
        progress.animateTo(
            targetValue = 1f,
            animationSpec = tween(2000, easing = LinearEasing)
        )

        currentState = LoadingState.COMPLETE
        showComplete = true
        delay(1500)
        onComplete()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        LoadingContent(
            progress = progress.value,
            currentState = currentState,
            showComplete = showComplete
        )
    }
}

/**
 * The content displayed during loading, separated for better organization
 */
@Composable
private fun LoadingContent(
    progress: Float,
    currentState: LoadingState,
    showComplete: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .padding(16.dp)
    ) {
        // Progress indicator (only shown before completion)
        if (!showComplete) {
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))
        }

        // Display loading state or completion message
        Box(
            modifier = Modifier.height(80.dp),
            contentAlignment = Alignment.Center
        ) {
            if (!showComplete) {
                // Display current loading state message
                Text(
                    text = currentState.message,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.animateContentSize()
                )
            } else {
                // Display completion message with animation
                CompletionMessage()
            }
        }
    }
}

/**
 * Animated completion message shown when loading is finished
 */
@Composable
private fun CompletionMessage() {
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = tween(800)),
        exit = fadeOut(animationSpec = tween(800))
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Everything is ready",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,  // Changed from primary to tertiary
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}