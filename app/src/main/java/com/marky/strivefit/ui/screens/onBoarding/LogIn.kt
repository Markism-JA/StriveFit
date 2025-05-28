package com.marky.strivefit.ui.screens.onBoarding

import android.util.Patterns
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.FormField
import com.marky.strivefit.ui.components.GoBackButton
import com.marky.strivefit.ui.components.GoogleButton
import com.marky.strivefit.ui.components.PasswordField
import com.marky.strivefit.ui.components.SignupPrompt
import com.marky.strivefit.ui.utilities.calculateWindowHeightSizeClass
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import com.marky.strivefit.ui.viewModel.AuthUiState
import com.marky.strivefit.ui.viewModel.AuthViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun Login(
    windowSizeClass: WindowSizeClass? = null,
    authViewModel: AuthViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onSuccessfulLogin: () -> Unit = {}, // Callback for successful login
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

    val paddingStart = if (isLandscape) 18.dp else 5.dp

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }

    var loginError by remember { mutableStateOf<String?>(null) }
    val authStateValue by authViewModel.authState.collectAsState()

    LaunchedEffect(authStateValue) {
        when (val state = authStateValue) {
            is AuthUiState.Success -> {
                loginError = null
                onSuccessfulLogin()
                authViewModel.resetAuthStateToIdle()
            }
            is AuthUiState.Error -> {
                loginError = state.message
                // ViewModel will be reset to Idle if user types or on next attempt
            }
            AuthUiState.Loading -> {
                loginError = null // Clear error when loading
            }
            AuthUiState.Idle -> {
                // Can also clear loginError here if it wasn't cleared by typing
                // if (loginError != null) loginError = null
            }
        }
    }

    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) { isVisible = true }

    val isEmailFormatValid = remember(email) { Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() }
    val canAttemptLogin = email.isNotBlank() && password.isNotBlank() // Simpler check for button enablement

    val onLoginClickedAction = {
        when {
            email.isBlank() || password.isBlank() -> {
                loginError = "Email and password cannot be empty."
            }

            !isEmailFormatValid -> {
                loginError = "Please enter a valid email format."
            }

            else -> {
                authViewModel.signInWithEmailAndPassword(email.trim(), password)
            }
        }
    }

    LaunchedEffect(email, password) { // Clear general error when user types
        if (loginError != null) {
            loginError = null
            if (authStateValue is AuthUiState.Error) {
                authViewModel.resetAuthStateToIdle()
            }
        }
    }

    val animatedOnBackClick = {
        scope.launch {
            isVisible = false
            delay(300)
            onBackClick()
        }
    }

    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 16.dp
        WindowHeightSizeClass.Medium -> 12.dp
        else -> 8.dp
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
            .padding(top = 20.dp)
            .padding(start = paddingStart, end = 5.dp)
    ) {
        AnimatedVisibility(
            visible = isVisible,
            enter = fadeIn(tween(500)) + slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = tween(500)
            ),
            exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = { it / 2 }, animationSpec = tween(300))
        ) {
            if (isLandscape) {
                LoginLandscapeLayout(
                    email = email,
                    onEmailChange = { email = it },
                    isEmailFocused = isEmailFocused,
                    onEmailFocusChanged = { isEmailFocused = it },
                    isEmailFormatValid = isEmailFormatValid,
                    password = password,
                    onPasswordChange = { if (it.length <= MAX_PASSWORD_LENGTH) password = it },
                    isPasswordVisible = isPasswordVisible,
                    onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
                    isPasswordFocused = isPasswordFocused,
                    onPasswordFocusChanged = { isPasswordFocused = it },
                    onBackClick = onBackClick,
                    onLoginClick = onLoginClickedAction,
                    onGoogleLoginClick = onGoogleLoginClick,
                    onForgotPasswordClick = onForgotPasswordClick,
                    onSignupClick = onSignupClick,
                    scrollState = scrollState,
                    verticalSpacing = verticalSpacing,
                    loginError = loginError,
                    isLoading = authStateValue is AuthUiState.Loading,
                    canAttemptLogin = canAttemptLogin
                )
            } else {
                LoginPortraitLayout(
                    email = email,
                    onEmailChange = { email = it },
                    isEmailFocused = isEmailFocused,
                    onEmailFocusChanged = { isEmailFocused = it },
                    isEmailFormatValid = isEmailFormatValid,
                    password = password,
                    onPasswordChange = { if (it.length <= MAX_PASSWORD_LENGTH) password = it },
                    isPasswordVisible = isPasswordVisible,
                    onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
                    isPasswordFocused = isPasswordFocused,
                    onPasswordFocusChanged = { isPasswordFocused = it },
                    onBackClick = onBackClick,
                    onLoginClick = onLoginClickedAction,
                    onGoogleLoginClick = onGoogleLoginClick,
                    onForgotPasswordClick = onForgotPasswordClick,
                    onSignupClick = onSignupClick,
                    scrollState = scrollState,
                    verticalSpacing = verticalSpacing,
                    loginError = loginError,
                    isLoading = authStateValue is AuthUiState.Loading,
                    canAttemptLogin = canAttemptLogin
                )
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
    isEmailFormatValid: Boolean,
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
    verticalSpacing: Dp,
    loginError: String?,
    isLoading: Boolean,
    canAttemptLogin: Boolean
) {
    // Specific error for email field if loginError indicates format issue or field is invalid & unfocused
    val emailFieldError = when {
        !isEmailFocused && email.isNotEmpty() && !isEmailFormatValid -> "Invalid email format."
        loginError != null && (loginError.contains("email", ignoreCase = true) || loginError.contains("user not found", ignoreCase = true) || loginError.contains("no account", ignoreCase = true)) -> loginError
        else -> null
    }
    // Password field shows the loginError if it's not specific to email or if it's a password error
    val passwordFieldError = if (loginError != null && emailFieldError == null && (loginError.contains("password", ignoreCase = true) || loginError.contains("credential", ignoreCase = true))) loginError else null
    // General error shown if not attributed to a specific field by above logic
    val generalLoginError = if (loginError != null && emailFieldError == null && passwordFieldError == null) loginError else null


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
            onFocusChanged = onEmailFocusChanged,
            isFieldValid = isEmailFormatValid && email.isNotEmpty(),
            errorMessage = emailFieldError
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
            errorMessage = passwordFieldError,
            passwordRequirements = null
        )

        if (generalLoginError != null) {
            Text(
                text = generalLoginError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
            )
        }

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
            text = if (isLoading) "Logging In..." else "Log In",
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && canAttemptLogin
        )

        OrLogInWithDivider(modifier = Modifier.padding(vertical = verticalSpacing))

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
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
private fun LoginLandscapeLayout(
    email: String,
    onEmailChange: (String) -> Unit,
    isEmailFocused: Boolean,
    onEmailFocusChanged: (Boolean) -> Unit,
    isEmailFormatValid: Boolean,
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
    verticalSpacing: Dp,
    loginError: String?,
    isLoading: Boolean,
    canAttemptLogin: Boolean
) {
    val emailFieldError = when {
        !isEmailFocused && email.isNotEmpty() && !isEmailFormatValid -> "Invalid email format."
        loginError != null && (loginError.contains("email", ignoreCase = true) || loginError.contains("user not found", ignoreCase = true) || loginError.contains("no account", ignoreCase = true)) -> loginError
        else -> null
    }
    val passwordFieldError = if (loginError != null && emailFieldError == null && (loginError.contains("password", ignoreCase = true) || loginError.contains("credential", ignoreCase = true))) loginError else null
    val generalLoginError = if (loginError != null && emailFieldError == null && passwordFieldError == null) loginError else null

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 80.dp)
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

        Spacer(modifier = Modifier.height(verticalSpacing))

        FormField(
            label = "Email",
            value = email,
            onValueChange = onEmailChange,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isFocused = isEmailFocused,
            onFocusChanged = onEmailFocusChanged,
            isFieldValid = isEmailFormatValid && email.isNotEmpty(),
            errorMessage = emailFieldError
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
            errorMessage = passwordFieldError,
            passwordRequirements = null
        )

        if (generalLoginError != null) {
            Text(
                text = generalLoginError,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp)
            )
        }

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
            text = if (isLoading) "Logging In..." else "Log In",
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading && canAttemptLogin
        )

        // For landscape, OrLogInWithDivider might be less common, but including for consistency
        OrLogInWithDivider(modifier = Modifier.padding(vertical = verticalSpacing))

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
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = verticalSpacing * 1.5f),
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
            .padding(vertical = 8.dp) // Added padding for better spacing around it
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
        Text(
            text = "Or log in with",
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f), // Adjusted alpha for subtlety
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )
    }
}