package com.marky.strivefit.ui.screens.onBoarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.FormField
import com.marky.strivefit.ui.components.GoBackButton
import com.marky.strivefit.ui.components.GoogleButton
import com.marky.strivefit.ui.components.PasswordField
import com.marky.strivefit.ui.components.SignupPrompt
import com.marky.strivefit.ui.utilities.calculateWindowHeightSizeClass
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Login(
    windowSizeClass: WindowSizeClass? = null,
    onBackClick: () -> Unit = {},
    onLoginClick: () -> Unit = {},
    onGoogleLoginClick: () -> Unit = {},
    onForgotPasswordClick: () -> Unit = {},
    onSignupClick: () -> Unit = {}
) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp
    val screenHeight = configuration.screenHeightDp.dp

    val widthSizeClass by remember(windowSizeClass, screenWidth) {
        mutableStateOf(windowSizeClass?.widthSizeClass ?: calculateWindowWidthSizeClass(screenWidth))
    }

    val heightSizeClass by remember(windowSizeClass, screenHeight) {
        mutableStateOf(windowSizeClass?.heightSizeClass ?: calculateWindowHeightSizeClass(screenHeight))
    }
    val aspectRatio = screenWidth / screenHeight
    val isLandscape = aspectRatio > 1.2f && widthSizeClass != WindowWidthSizeClass.Compact

    val paddingStart = when (isLandscape) {
        true -> 18.dp
        false -> 5.dp
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        isVisible = true
    }

    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 16.dp
        WindowHeightSizeClass.Medium -> 12.dp
        else -> 8.dp
    }

    val animatedOnBackClick = {
        scope.launch {
            isVisible = false
            delay(300) // Match animation duration
            onBackClick()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .padding(top = 20.dp)
            .padding(start = paddingStart, end = 5.dp) // Use start instead of just paddingStart for clarity
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(500)) + slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(500)
            ),
            exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = { it / 2 }, animationSpec = tween(300))
        ) {
            when {
                isLandscape -> {
                    LoginLandscapeLayout(
                        email = email,
                        onEmailChange = { email = it },
                        isEmailFocused = isEmailFocused,
                        onEmailFocusChanged = { isEmailFocused = it },
                        password = password,
                        onPasswordChange = { password = it },
                        isPasswordVisible = isPasswordVisible,
                        onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
                        isPasswordFocused = isPasswordFocused,
                        onPasswordFocusChanged = { isPasswordFocused = it },
                        onBackClick = onBackClick,
                        onLoginClick = onLoginClick,
                        onGoogleLoginClick = onGoogleLoginClick,
                        onForgotPasswordClick = onForgotPasswordClick,
                        onSignupClick = onSignupClick,
                        scrollState = scrollState,
                        verticalSpacing = verticalSpacing
                    )
                }
                else -> {
                    LoginPortraitLayout(
                        email = email,
                        onEmailChange = { email = it },
                        isEmailFocused = isEmailFocused,
                        onEmailFocusChanged = { isEmailFocused = it },
                        password = password,
                        onPasswordChange = { password = it },
                        isPasswordVisible = isPasswordVisible,
                        onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
                        isPasswordFocused = isPasswordFocused,
                        onPasswordFocusChanged = { isPasswordFocused = it },
                        onBackClick = onBackClick,
                        onLoginClick = onLoginClick,
                        onGoogleLoginClick = onGoogleLoginClick,
                        onForgotPasswordClick = onForgotPasswordClick,
                        onSignupClick = onSignupClick,
                        scrollState = scrollState,
                        verticalSpacing = verticalSpacing
                    )
                }
            }
        }
    }
}

@Composable
private fun LoginPortraitLayout(
    email: String,
    onEmailChange: (String) -> Unit,
    isEmailFocused: Boolean,
    onEmailFocusChanged: (Boolean) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    isPasswordFocused: Boolean,
    onPasswordFocusChanged: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignupClick: () -> Unit,
    scrollState: ScrollState,
    verticalSpacing: Dp
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalSpacing)
        ) {
            GoBackButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = "Log In",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        FormField(
            label = "Email",
            value = email,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isFocused = isEmailFocused,
            onFocusChanged = onEmailFocusChanged
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        PasswordField(
            label = "Password",
            value = password,
            onValueChange = onPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            isFocused = isPasswordFocused,
            onFocusChanged = onPasswordFocusChanged,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp) // Can use verticalSpacing or a fixed value
        ) {
            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "Forgot Password?",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(verticalSpacing * 1.5f)) // Adjusted spacer

        AnimatedCustomButton(
            onClick = onLoginClick,
            text = "Log In",
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        OrLogInWithDivider(modifier = Modifier.padding(vertical = verticalSpacing))

        GoogleButton(
            onClick = onGoogleLoginClick,
            text = "Continue with Google",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalSpacing) // Added padding for consistency
        )

        Spacer(modifier = Modifier.height(verticalSpacing))


        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 24.dp), // Ensure SignupPrompt is at the bottom or appropriately spaced
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SignupPrompt(
                onSignupClick = onSignupClick,
                animationTriggered = true // Assuming this should always be true for login screen
            )
        }
    }
}

@Composable
private fun LoginLandscapeLayout(
    email: String,
    onEmailChange: (String) -> Unit,
    isEmailFocused: Boolean,
    onEmailFocusChanged: (Boolean) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    isPasswordFocused: Boolean,
    onPasswordFocusChanged: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    onLoginClick: () -> Unit,
    onGoogleLoginClick: () -> Unit,
    onForgotPasswordClick: () -> Unit,
    onSignupClick: () -> Unit,
    scrollState: ScrollState,
    verticalSpacing: Dp // Use this for consistent spacing
) {
    // For Login, landscape can still be a single column due to fewer fields.
    // If more complex layout (e.g. 2 columns for fields vs buttons) is needed,
    // it would be implemented here, similar to SignUpLandscapeLayout's Row structure.
    // For now, using a single column layout similar to portrait.
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 80.dp) // Add some horizontal padding for landscape
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalSpacing)
        ) {
            GoBackButton(
                onClick = onBackClick,
                modifier = Modifier.align(Alignment.CenterStart)
            )

            Text(
                text = "Log In",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(verticalSpacing)) // Adjusted spacer for landscape

        FormField(
            label = "Email",
            value = email,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isFocused = isEmailFocused,
            onFocusChanged = onEmailFocusChanged
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        PasswordField(
            label = "Password",
            value = password,
            onValueChange = onPasswordChange,
            isPasswordVisible = isPasswordVisible,
            onTogglePasswordVisibility = onTogglePasswordVisibility,
            isFocused = isPasswordFocused,
            onFocusChanged = onPasswordFocusChanged,
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            TextButton(
                onClick = onForgotPasswordClick,
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Text(
                    text = "Forgot Password?",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }

        Spacer(modifier = Modifier.height(verticalSpacing * 1.5f))

        AnimatedCustomButton(
            onClick = onLoginClick,
            text = "Log In",
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth()
        )

        GoogleButton(
            onClick = onGoogleLoginClick,
            text = "Continue with Google",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalSpacing)
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        Row(
            modifier = Modifier.fillMaxWidth().padding(top = verticalSpacing * 1.5f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            SignupPrompt(
                onSignupClick = onSignupClick,
                animationTriggered = true
            )
        }
    }
}

@Composable
private fun OrLogInWithDivider(modifier: Modifier = Modifier) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp, // Common thickness
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = "Or log in with",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
            style = MaterialTheme.typography.bodySmall, // subtle text
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}
