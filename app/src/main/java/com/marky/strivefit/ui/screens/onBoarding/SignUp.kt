package com.marky.strivefit.ui.screens.onBoaording

import LegalContentType
import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.common.api.ApiException
import com.marky.strivefit.R
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.FormField
import com.marky.strivefit.ui.components.GoBackButton
import com.marky.strivefit.ui.components.GoogleButton
import com.marky.strivefit.ui.components.PasswordField
import com.marky.strivefit.ui.screens.onBoarding.LegalContentDialog
import com.marky.strivefit.ui.utilities.calculateWindowHeightSizeClass
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import com.marky.strivefit.ui.viewModel.SignUpEvent
import com.marky.strivefit.ui.viewModel.SignUpResult
import com.marky.strivefit.ui.viewModel.SignUpUiState
import com.marky.strivefit.ui.viewModel.SignUpViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    signUpViewModel: SignUpViewModel = hiltViewModel(),
    windowSizeClass: WindowSizeClass? = null,
    onBackClick: () -> Unit = {},
    onSignUpSuccess: (String) -> Unit = {}
) {
    val uiState by signUpViewModel.uiState.collectAsStateWithLifecycle()
    val snakebarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()


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
                    signUpViewModel.onEvent(SignUpEvent.GoogleSignInSucceeded(idToken))
                } else {
                }
            } catch (e: ApiException) {
            }
        }
    }


    LaunchedEffect(key1 = true) {
        signUpViewModel.resultFlow.collect { result ->
            when (result) {
                is SignUpResult.Success -> {
                    onSignUpSuccess(result.userId)
                }

                is SignUpResult.Error -> {
                    // Show the error message from the ViewModel.
                    snakebarHostState.showSnackbar(result.message)
                }

                is SignUpResult.LaunchGoogleSignIn -> {
                    oneTapClient.beginSignIn(result.signInRequest)
                        .addOnSuccessListener { beginSignInResult ->
                            try {
                                googleSignInLauncher.launch(
                                    IntentSenderRequest.Builder(
                                        beginSignInResult.pendingIntent.intentSender
                                    ).build()
                                )
                            } catch (e: Exception) {
                            }
                        }
                }
            }
        }
    }

    var isVisible by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { isVisible = true }
    val scrollState = rememberScrollState()

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

    var showLegalDialog by remember { mutableStateOf<LegalContentType?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier
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
                exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = {it/2}, animationSpec = tween(300))
            ) {
                when {
                    isLandscape -> {
                        SignUpLandscapeLayout(
                                state = uiState,
                                onEvent = signUpViewModel::onEvent,
                                heightSizeClass = heightSizeClass,
                                onBackClick = {
                                    // We can still add transition animations to UI events
                                    scope.launch {
                                        isVisible = false
                                        delay(300)
                                        onBackClick()
                                    }
                                },
                                onGoogleSignUpClick = { signUpViewModel.onEvent(SignUpEvent.GoogleSignUpClicked) },
                                onTermsClick = { showLegalDialog = LegalContentType.TERMS_OF_SERVICE },
                                onPrivacyClick = { showLegalDialog = LegalContentType.PRIVACY_POLICY },
                                scrollState = scrollState)
                    }
                    else -> {
                        SignUpPortraitLayout(
                                state = uiState,
                                onEvent = signUpViewModel::onEvent,
                                heightSizeClass = heightSizeClass,
                                onBackClick = {
                                    // We can still add transition animations to UI events
                                    scope.launch {
                                        isVisible = false
                                        delay(300)
                                        onBackClick()
                                    }
                                },
                                onGoogleSignUpClick = { signUpViewModel.onEvent(SignUpEvent.GoogleSignUpClicked) },
                                onTermsClick = { showLegalDialog = LegalContentType.TERMS_OF_SERVICE },
                                onPrivacyClick = { showLegalDialog = LegalContentType.PRIVACY_POLICY },
                                scrollState = scrollState)
                    }
                }
            }
        }

        if (uiState.isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background.copy(alpha = 0.7f)) // Semi-transparent overlay
                    .clickable(enabled = false, onClick = {}), // Consume clicks
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
        }

        SnackbarHost(
            hostState = snakebarHostState,
            modifier = Modifier.align(Alignment.BottomCenter).padding(bottom = 16.dp)
        )

        if (!isLoading) {
            showLegalDialog?.let { type ->
                LegalContentDialog(
                    type = type,
                    onDismiss = { showLegalDialog = null },
                    isLandscape = isLandscape
                )
            }
        }
    }
}

@Composable
private fun SignUpPortraitLayout(
    state: SignUpUiState,
    onEvent: (SignUpEvent) -> Unit,
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    onGoogleSignUpClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    scrollState: ScrollState
) {
    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 16.dp
        WindowHeightSizeClass.Medium -> 12.dp
        else -> 8.dp
    }

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
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(verticalSpacing))

        FormField(
            label = "Full Name",
            value = state.fullName,
            onValueChange = { onEvent(SignUpEvent.FullNameChanged(it)) },
            isFocused = state.hasFullNameBeenFocused,
            onFocusChanged = { onEvent(SignUpEvent.FullNameFocusLost) },
            errorMessage = state.fullNameError,
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        FormField(
            label = "Email",
            value = state.email,
            onValueChange = { onEvent(SignUpEvent.EmailChanged(it)) },
            isFocused = state.hasEmailBeenFocused,
            onFocusChanged = { onEvent(SignUpEvent.EmailFocusLost) },
            errorMessage = state.emailError,
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        PasswordField(
            label = "Password",
            value = state.password,
            onValueChange = { onEvent(SignUpEvent.PasswordChanged(it)) },
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = { onEvent(SignUpEvent.TogglePasswordVisibility)},
            isFocused = state.hasPasswordFocused,
            onFocusChanged = { onEvent(SignUpEvent.PasswordFocusLost)},
            passwordRequirements = state.passwordRequirements,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        PasswordField(
            label = "Confirm Password",
            value = state.confirmPassword,
            onValueChange = { onEvent(SignUpEvent.ConfirmPasswordChanged(it)) },
            isPasswordVisible = state.isConfirmPasswordVisible,
            onTogglePasswordVisibility = { onEvent(SignUpEvent.ToggleConfirmPasswordVisibility)},
            isFocused = state.hasConfirmPasswordFocused,
            onFocusChanged = { onEvent(SignUpEvent.ConfirmPasswordFocusLost)},
            errorMessage = state.confirmPasswordError,
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        TermsAndConditionsCheckbox(
            isChecked = state.isTermsAccepted,
            onCheckedChange = { onEvent(SignUpEvent.TermsAcceptedChanged(it)) },
            onTermsClick = onTermsClick,
            onPrivacyClick = onPrivacyClick,
            enabled = !state.isLoading
        )

        Spacer(modifier = Modifier.height(verticalSpacing * 1.5f))

        AnimatedCustomButton(
            onClick = { onEvent(SignUpEvent.SignUpClicked) },
            text = "Sign up",
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isSignUpButtonEnabled // Specific enabled state for this button
        )


        GoogleButton(
            onClick = onGoogleSignUpClick,
            text = "Continue with Google",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalSpacing),
        )
    }
}

@Composable
private fun SignUpLandscapeLayout(
    state: SignUpUiState,
    onEvent: (SignUpEvent) -> Unit,
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    onGoogleSignUpClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    scrollState: ScrollState
) {
    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 12.dp
        WindowHeightSizeClass.Medium -> 8.dp
        else -> 6.dp
    }

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
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(verticalSpacing))

        FormField(
            label = "Full Name",
            value = state.fullName,
            onValueChange = { onEvent(SignUpEvent.FullNameChanged(it)) },
            isFocused = state.hasFullNameBeenFocused,
            onFocusChanged = { onEvent(SignUpEvent.FullNameFocusLost) },
            errorMessage = state.fullNameError,
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        FormField(
            label = "Email",
            value = state.email,
            onValueChange = { onEvent(SignUpEvent.EmailChanged(it)) },
            isFocused = state.hasEmailBeenFocused,
            onFocusChanged = { onEvent(SignUpEvent.EmailFocusLost) },
            errorMessage = state.emailError,
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        PasswordField(
            label = "Password",
            value = state.password,
            onValueChange = { onEvent(SignUpEvent.PasswordChanged(it)) },
            isPasswordVisible = state.isPasswordVisible,
            onTogglePasswordVisibility = { onEvent(SignUpEvent.TogglePasswordVisibility)},
            isFocused = state.hasPasswordFocused,
            onFocusChanged = { onEvent(SignUpEvent.PasswordFocusLost)},
            passwordRequirements = state.passwordRequirements,
            errorMessage = null,
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        PasswordField(
            label = "Confirm Password",
            value = state.confirmPassword,
            onValueChange = { onEvent(SignUpEvent.ConfirmPasswordChanged(it)) },
            isPasswordVisible = state.isConfirmPasswordVisible,
            onTogglePasswordVisibility = { onEvent(SignUpEvent.ToggleConfirmPasswordVisibility)},
            isFocused = state.hasConfirmPasswordFocused,
            onFocusChanged = { onEvent(SignUpEvent.ConfirmPasswordFocusLost)},
            errorMessage = state.confirmPasswordError,
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        TermsAndConditionsCheckbox(
            isChecked = state.isTermsAccepted,
            onCheckedChange = { onEvent(SignUpEvent.TermsAcceptedChanged(it)) },
            onTermsClick = onTermsClick,
            onPrivacyClick = onPrivacyClick,
            enabled = !state.isLoading
        )

        Spacer(modifier = Modifier.height(verticalSpacing * 1.5f))

        AnimatedCustomButton(
            onClick = { onEvent(SignUpEvent.SignUpClicked) },
            text = "Sign up",
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.isSignUpButtonEnabled // Specific enabled state for this button
        )


        GoogleButton(
            onClick = onGoogleSignUpClick,
            text = "Continue with Google",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalSpacing),
        )
    }
}

@Composable
private fun TermsAndConditionsCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(vertical = 8.dp)
            .clickable(enabled = enabled, onClick = { onCheckedChange(!isChecked) }) // Make row clickable to toggle
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.outline,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.size(24.dp),
            enabled = enabled
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "I agree to the ",
            style = MaterialTheme.typography.bodyMedium,
            color = if (enabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f)
        )
        Text(
            text = "Terms of Service",
            style = MaterialTheme.typography.bodyMedium,
            color = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(enabled = enabled) { onTermsClick() }
        )
        Text(
            text = " & ",
            style = MaterialTheme.typography.bodyMedium,
            color = if (enabled) MaterialTheme.colorScheme.onBackground else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.38f)
        )
        Text(
            text = "Privacy Policy",
            style = MaterialTheme.typography.bodyMedium,
            color = if (enabled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.primary.copy(alpha = 0.38f),
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable(enabled = enabled) { onPrivacyClick() }
        )
    }
}