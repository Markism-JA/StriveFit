package com.marky.strivefit.ui.screens.onBoarding

import android.app.Activity
import android.content.IntentSender
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import android.util.Patterns
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.marky.strivefit.R
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

const val MAX_PASSWORD_LENGTH = 64

@Composable
fun Login(
    windowSizeClass: WindowSizeClass? = null,
    authViewModel: AuthViewModel = hiltViewModel(),
    onBackClick: () -> Unit = {},
    onSuccessfulLogin: (String) -> Unit = {},
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
    var isLoading by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val oneTapClient = remember { Identity.getSignInClient(context) }
    val webClientId = stringResource(id = R.string.default_web_client_id)

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            try {
                val credential = oneTapClient.getSignInCredentialFromIntent(result.data)
                val idToken = credential.googleIdToken
                if (idToken != null) {
                    authViewModel.signInWithGoogle(idToken)
                } else {
                    loginError = "Google Sign-In failed: idToken is null"
                }
            } catch (e: ApiException) {
                loginError = "Google Sign-In failed: ${e.localizedMessage}"
            }
        }
    }

    val onGoogleLoginClick: () -> Unit = {
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(webClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .build()

        oneTapClient.beginSignIn(signInRequest)
            .addOnSuccessListener { result ->
                try {
                    googleSignInLauncher.launch(
                        IntentSenderRequest.Builder(
                            result.pendingIntent.intentSender
                        ).build()
                    )
                } catch (e: Exception) {
                    loginError = "Google Sign-In failed: ${e.localizedMessage}"
                }
            }
            .addOnFailureListener { e ->
                loginError = "Google Sign-In failed: ${e.localizedMessage}"
            }
    }

    LaunchedEffect(authStateValue) {
        when (val state = authStateValue) {
            is AuthUiState.Loading -> {
                isLoading = true
                loginError = null // Clear error when loading
            }
            is AuthUiState.Success -> {
                isLoading = false
                loginError = null
                onSuccessfulLogin(state.userId)
                authViewModel.resetAuthStateToIdle() // Reset after navigation
            }
            is AuthUiState.Error -> {
                isLoading = false
                loginError = state.message
            }
            is AuthUiState.Idle -> {
                isLoading = false
            }
        }
    }

    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) { isVisible = true }

    val isEmailFormatValid = remember(email) { Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() }
    val canAttemptLogin = email.isNotBlank() && password.isNotBlank()

    val performLoginAction = {
        if (!isLoading) { // Check if not loading before proceeding
            when {
                email.isBlank() || password.isBlank() -> {
                    loginError = "Email and password cannot be empty."
                }
                !isEmailFormatValid && email.isNotEmpty() -> {
                    loginError = "Please enter a valid email format."
                }
                else -> {
                    authViewModel.signInWithEmailAndPassword(email.trim(), password)
                }
            }
        }
        // If isLoading is true, this lambda executes but the 'if' condition prevents main logic.
    }

    LaunchedEffect(email, password) {
        if (loginError != null) {
            loginError = null
        }
        if (authStateValue is AuthUiState.Error) {
            authViewModel.resetAuthStateToIdle()
        }
    }

    val animatedOnBackClick = {
        if (!isLoading) { // Check if not loading before proceeding
            scope.launch {
                isVisible = false
                delay(300) // Wait for exit animation
                onBackClick()
            }
        }
        // If isLoading is true, this lambda executes but the 'if' condition prevents main logic.
    }

    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 16.dp
        WindowHeightSizeClass.Medium -> 12.dp
        else -> 8.dp
    }

    val areActionsEnabled = !isLoading


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column( // Main content column
            modifier = Modifier
                .fillMaxSize()
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
                        password = password,
                        onPasswordChange = { if (it.length <= MAX_PASSWORD_LENGTH) password = it },
                        isPasswordVisible = isPasswordVisible,
                        onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
                        isPasswordFocused = isPasswordFocused,
                        onPasswordFocusChanged = { isPasswordFocused = it },
                        onBackClick = animatedOnBackClick,
                        onLoginClick = performLoginAction,
                        onGoogleLoginClick = onGoogleLoginClick,
                        onForgotPasswordClick = { if (areActionsEnabled) onForgotPasswordClick() },
                        onSignupClick = { if (areActionsEnabled) onSignupClick() },
                        scrollState = scrollState,
                        verticalSpacing = verticalSpacing,
                        loginError = loginError,
                        isLoading = isLoading,
                        canAttemptLogin = canAttemptLogin,
                        areActionsEnabled = areActionsEnabled
                    )
                } else {
                    LoginPortraitLayout(
                        email = email,
                        onEmailChange = { email = it },
                        isEmailFocused = isEmailFocused,
                        onEmailFocusChanged = { isEmailFocused = it },
                        password = password,
                        onPasswordChange = { if (it.length <= MAX_PASSWORD_LENGTH) password = it },
                        isPasswordVisible = isPasswordVisible,
                        onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
                        isPasswordFocused = isPasswordFocused,
                        onPasswordFocusChanged = { isPasswordFocused = it },
                        onBackClick = animatedOnBackClick,
                        onLoginClick = performLoginAction,
                        onGoogleLoginClick = onGoogleLoginClick,
                        onForgotPasswordClick = { if (areActionsEnabled) onForgotPasswordClick() },
                        onSignupClick = { if (areActionsEnabled) onSignupClick() },
                        scrollState = scrollState,
                        verticalSpacing = verticalSpacing,
                        loginError = loginError,
                        isLoading = isLoading,
                        canAttemptLogin = canAttemptLogin,
                        areActionsEnabled = areActionsEnabled
                    )
                }
            }
        }

        // Loading Overlay
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f))
                    .clickable(enabled = false, onClick = {}), // Consume clicks
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
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
    verticalSpacing: Dp,
    loginError: String?,
    isLoading: Boolean,
    canAttemptLogin: Boolean,
    areActionsEnabled: Boolean
) {
    val emailFieldError = when {
        !isEmailFocused && email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Invalid email format."
        loginError != null && (loginError.contains("email", ignoreCase = true) ||
                loginError.contains("user not found", ignoreCase = true) ||
                loginError.contains("no account", ignoreCase = true) ||
                loginError.equals("Email and password cannot be empty.", ignoreCase = true) && email.isBlank())
            -> loginError
        else -> null
    }
    val passwordFieldError = when {
        loginError != null && emailFieldError == null &&
                (loginError.contains("password", ignoreCase = true) ||
                        loginError.contains("credential", ignoreCase = true) ||
                        loginError.equals("Email and password cannot be empty.", ignoreCase = true) && password.isBlank())
            -> loginError
        else -> null
    }
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
                modifier = Modifier.align(Alignment.CenterStart),
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
            errorMessage = if (isLoading) null else emailFieldError,
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
            errorMessage = if (isLoading) null else passwordFieldError,
            passwordRequirements = null,
        )

        if (!isLoading && generalLoginError != null) {
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
                modifier = Modifier.align(Alignment.CenterEnd),
                enabled = areActionsEnabled
            ) {
                Text(
                    text = "Forgot Password?",
                    color = if (areActionsEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
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
            enabled = areActionsEnabled && canAttemptLogin
        )


        GoogleButton(
            onClick = { onGoogleLoginClick() },
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
                animationTriggered = true,
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
    verticalSpacing: Dp,
    loginError: String?,
    isLoading: Boolean,
    canAttemptLogin: Boolean,
    areActionsEnabled: Boolean
) {
    val emailFieldError = when {
        !isEmailFocused && email.isNotEmpty() && !Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() -> "Invalid email format."
        loginError != null && (loginError.contains("email", ignoreCase = true) ||
                loginError.contains("user not found", ignoreCase = true) ||
                loginError.contains("no account", ignoreCase = true) ||
                loginError.equals("Email and password cannot be empty.", ignoreCase = true) && email.isBlank())
            -> loginError
        else -> null
    }
    val passwordFieldError = when {
        loginError != null && emailFieldError == null &&
                (loginError.contains("password", ignoreCase = true) ||
                        loginError.contains("credential", ignoreCase = true) ||
                        loginError.equals("Email and password cannot be empty.", ignoreCase = true) && password.isBlank())
            -> loginError
        else -> null
    }
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
                modifier = Modifier.align(Alignment.CenterStart),
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
            errorMessage = if (isLoading) null else emailFieldError,
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
            errorMessage = if (isLoading) null else passwordFieldError,
            passwordRequirements = null,
        )

        if (!isLoading && generalLoginError != null) {
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
                modifier = Modifier.align(Alignment.CenterEnd),
                enabled = areActionsEnabled
            ) {
                Text(
                    text = "Forgot Password?",
                    color = if (areActionsEnabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
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
            enabled = areActionsEnabled && canAttemptLogin
        )


        GoogleButton(
            onClick = { onGoogleLoginClick() },
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
                animationTriggered = true,
            )
        }
    }
}