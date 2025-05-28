package com.marky.strivefit.ui.screens.onBoarding

//import androidx.compose.material3.HorizontalDivider // Not used in the provided snippet directly, but good practice to keep if used elsewhere
import LegalContentType
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
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.marky.strivefit.ui.components.AnimatedCustomButton
import com.marky.strivefit.ui.components.FormField
import com.marky.strivefit.ui.components.GoBackButton
import com.marky.strivefit.ui.components.GoogleButton
import com.marky.strivefit.ui.components.PasswordField
import com.marky.strivefit.ui.components.PasswordFieldRequirementStatus
import com.marky.strivefit.ui.utilities.calculateWindowHeightSizeClass
import com.marky.strivefit.ui.utilities.calculateWindowWidthSizeClass
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SignUp(
    windowSizeClass: WindowSizeClass? = null,
    onBackClick: () -> Unit = {},
    onSignUpClick: () -> Unit = {},
    onGoogleSignUpClick: () -> Unit = {}
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

    var fullName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }
    var isConfirmPasswordVisible by remember { mutableStateOf(false) }
    var isTermsAccepted by remember { mutableStateOf(false) }

    var isFullNameFocused by remember { mutableStateOf(false) }
    var isEmailFocused by remember { mutableStateOf(false) }
    var isPasswordFocused by remember { mutableStateOf(false) }
    var isConfirmPasswordFocused by remember { mutableStateOf(false) }

    val scope = rememberCoroutineScope()
    var isVisible by remember { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    // State for managing the legal content dialog
    var showLegalDialog by remember { mutableStateOf<LegalContentType?>(null) }

    val isPasswordStrongEnough = remember(password) {
        hasMinLength(password) && hasUppercase(password) && hasLowercase(password) && hasDigit(password) && hasSpecialChar(password)
    }
    val arePasswordsMatching = remember(password, confirmPassword) {
        password == confirmPassword
    }
    val canEnableSignUpButton = remember(fullName, email, password, confirmPassword, isPasswordStrongEnough, arePasswordsMatching, isTermsAccepted) {
        isValidFullName(fullName) && isValidEmail(email) &&
                password.isNotEmpty() && confirmPassword.isNotEmpty() &&
                isPasswordStrongEnough && arePasswordsMatching &&
                isTermsAccepted
    }

    LaunchedEffect(Unit) {
        isVisible = true
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
            exit = fadeOut(tween(300)) + slideOutVertically(targetOffsetY = {it/2}, animationSpec = tween(300))
        ) {
            when {
                isLandscape -> {
                    SignUpLandscapeLayout(
                        fullName = fullName,
                        onFullNameChange = { fullName = it },
                        isFullNameFocused = isFullNameFocused,
                        onFullNameFocusChanged = { isFullNameFocused = it },
                        email = email,
                        onEmailChange = { email = it },
                        isEmailFocused = isEmailFocused,
                        onEmailFocusChanged = { isEmailFocused = it },
                        password = password,
                        onPasswordChange = { password = it },
                        isPasswordVisible = isPasswordVisible,
                        onTogglePasswordVisibility = { isPasswordVisible = !isPasswordVisible },
                        onToggleConfirmPasswordVisibility = { isConfirmPasswordVisible = !isConfirmPasswordVisible },
                        isPasswordFocused = isPasswordFocused,
                        onPasswordFocusChanged = { isPasswordFocused = it },
                        confirmPassword = confirmPassword,
                        onConfirmPasswordChange = { confirmPassword = it },
                        isConfirmPasswordVisible = isConfirmPasswordVisible,
                        isConfirmPasswordFocused = isConfirmPasswordFocused,
                        onConfirmPasswordFocusChanged = { isConfirmPasswordFocused = it },
                        isTermsAccepted = isTermsAccepted,
                        onTermsAcceptedChange = { isTermsAccepted = it },
                        heightSizeClass = heightSizeClass,
                        onBackClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onBackClick()
                            }
                        },
                        onSignUpClick = onSignUpClick,
                        onGoogleSignUpClick = onGoogleSignUpClick,
                        onTermsClick = { showLegalDialog = LegalContentType.TERMS_OF_SERVICE },
                        onPrivacyClick = { showLegalDialog = LegalContentType.PRIVACY_POLICY },
                        scrollState = scrollState,
                        isSignUpEnabled = canEnableSignUpButton
                    )
                }
                else -> {
                    SignUpPortraitLayout(
                        fullName = fullName,
                        onFullNameChange = { fullName = it },
                        isFullNameFocused = isFullNameFocused,
                        onFullNameFocusChanged = { isFullNameFocused = it },
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
                        confirmPassword = confirmPassword,
                        onConfirmPasswordChange = { confirmPassword = it },
                        isConfirmPasswordVisible = isConfirmPasswordVisible,
                        onToggleConfirmPasswordVisibility = { isConfirmPasswordVisible = !isConfirmPasswordVisible },
                        isConfirmPasswordFocused = isConfirmPasswordFocused,
                        onConfirmPasswordFocusChanged = { isConfirmPasswordFocused = it },
                        isTermsAccepted = isTermsAccepted,
                        onTermsAcceptedChange = { isTermsAccepted = it },
                        heightSizeClass = heightSizeClass,
                        onBackClick = {
                            scope.launch {
                                isVisible = false
                                delay(300)
                                onBackClick()
                            }
                        },
                        onSignUpClick = onSignUpClick,
                        onGoogleSignUpClick = onGoogleSignUpClick,
                        onTermsClick = { showLegalDialog = LegalContentType.TERMS_OF_SERVICE },
                        onPrivacyClick = { showLegalDialog = LegalContentType.PRIVACY_POLICY },
                        scrollState = scrollState,
                        isSignUpEnabled = canEnableSignUpButton
                    )
                }
            }
        }
        showLegalDialog?.let { type ->
            LegalContentDialog(
                type = type,
                onDismiss = { showLegalDialog = null },
                isLandscape = isLandscape
            )
        }
    }
}

@Composable
private fun SignUpPortraitLayout(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    isFullNameFocused: Boolean,
    onFullNameFocusChanged: (Boolean) -> Unit,
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
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    isConfirmPasswordVisible: Boolean,
    onToggleConfirmPasswordVisibility: () -> Unit,
    isConfirmPasswordFocused: Boolean,
    onConfirmPasswordFocusChanged: (Boolean) -> Unit,
    isTermsAccepted: Boolean,
    onTermsAcceptedChange: (Boolean) -> Unit,
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleSignUpClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    isSignUpEnabled: Boolean,
    scrollState: ScrollState
) {
    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 16.dp
        WindowHeightSizeClass.Medium -> 12.dp
        else -> 8.dp
    }
    val passwordRequirementsList = remember(password, isPasswordFocused)
    {
        if (isPasswordFocused) {
            listOf(
                PasswordFieldRequirementStatus("Minimum 8 characters", hasMinLength(password)),
                PasswordFieldRequirementStatus("1 uppercase", hasUppercase(password)),
                PasswordFieldRequirementStatus("1 lowercase", hasLowercase(password)),
                PasswordFieldRequirementStatus("1 digit", hasDigit(password)),
                PasswordFieldRequirementStatus("1 special char", hasSpecialChar(password))
                )
        } else {
            null
        }
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
                modifier = Modifier.align(Alignment.CenterStart)
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
            value = fullName,
            onValueChange = onFullNameChange,
            isFocused = isFullNameFocused,
            onFocusChanged = onFullNameFocusChanged
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

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
            passwordRequirements = passwordRequirementsList,
            errorMessage = null
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        PasswordField(
            label = "Confirm Password",
            value = confirmPassword,
            onValueChange = onConfirmPasswordChange,
            isPasswordVisible = isConfirmPasswordVisible,
            onTogglePasswordVisibility = onToggleConfirmPasswordVisibility,
            isFocused = isConfirmPasswordFocused,
            onFocusChanged = onConfirmPasswordFocusChanged,
            errorMessage = if (confirmPassword.isNotEmpty() && password != confirmPassword && (isConfirmPasswordFocused || password.length >= confirmPassword.length)) {
                "Passwords do not match"
            } else {
                null
            }
        )

        Spacer(modifier = Modifier.height(verticalSpacing))

        TermsAndConditionsCheckbox(
            isChecked = isTermsAccepted,
            onCheckedChange = onTermsAcceptedChange,
            onTermsClick = onTermsClick,
            onPrivacyClick = onPrivacyClick
        )

        Spacer(modifier = Modifier.height(verticalSpacing * 1.5f))

        AnimatedCustomButton(
            onClick = onSignUpClick,
            text = "Sign up",
            backgroundColor = MaterialTheme.colorScheme.primary,
            textColor = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.fillMaxWidth(),
            enabled = isSignUpEnabled
        )


        GoogleButton(
            onClick = onGoogleSignUpClick,
            text = "Continue with Google",
            backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
            textColor = MaterialTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = verticalSpacing)
        )
    }
}

@Composable
private fun SignUpLandscapeLayout(
    fullName: String,
    onFullNameChange: (String) -> Unit,
    isFullNameFocused: Boolean,
    onFullNameFocusChanged: (Boolean) -> Unit,
    email: String,
    onEmailChange: (String) -> Unit,
    isEmailFocused: Boolean,
    onEmailFocusChanged: (Boolean) -> Unit,
    password: String,
    onPasswordChange: (String) -> Unit,
    isPasswordVisible: Boolean,
    onTogglePasswordVisibility: () -> Unit,
    onToggleConfirmPasswordVisibility: () -> Unit,
    isPasswordFocused: Boolean,
    onPasswordFocusChanged: (Boolean) -> Unit,
    confirmPassword: String,
    onConfirmPasswordChange: (String) -> Unit,
    isConfirmPasswordVisible: Boolean,
    isConfirmPasswordFocused: Boolean,
    onConfirmPasswordFocusChanged: (Boolean) -> Unit,
    isTermsAccepted: Boolean,
    onTermsAcceptedChange: (Boolean) -> Unit,
    heightSizeClass: WindowHeightSizeClass,
    onBackClick: () -> Unit,
    onSignUpClick: () -> Unit,
    onGoogleSignUpClick: () -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    scrollState: ScrollState,
    isSignUpEnabled: Boolean
) {
    val verticalSpacing = when (heightSizeClass) {
        WindowHeightSizeClass.Expanded -> 12.dp
        WindowHeightSizeClass.Medium -> 8.dp
        else -> 6.dp
    }

    val passwordRequirementsList = remember(password, isPasswordFocused)
    {
        if (isPasswordFocused) {
            listOf(
                PasswordFieldRequirementStatus("Minimum 8 characters", hasMinLength(password)),
                PasswordFieldRequirementStatus("1 uppercase", hasUppercase(password)),
                PasswordFieldRequirementStatus("1 lowercase", hasLowercase(password)),
                PasswordFieldRequirementStatus("1 digit", hasDigit(password)),
                PasswordFieldRequirementStatus("1 special char", hasSpecialChar(password))
            )
        } else {
            null
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(horizontal = 16.dp)
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
                text = "Create Account",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(verticalSpacing))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(verticalSpacing)
            ) {
                FormField(
                    label = "Full Name",
                    value = fullName,
                    onValueChange = onFullNameChange,
                    isFocused = isFullNameFocused,
                    onFocusChanged = onFullNameFocusChanged
                )

                FormField(
                    label = "Email",
                    value = email,
                    onValueChange = onEmailChange,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isFocused = isEmailFocused,
                    onFocusChanged = onEmailFocusChanged
                )
            }

            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(verticalSpacing)
            ) {
                PasswordField(
                    label = "Password",
                    value = password,
                    onValueChange = onPasswordChange,
                    isPasswordVisible = isPasswordVisible,
                    onTogglePasswordVisibility = onTogglePasswordVisibility,
                    isFocused = isPasswordFocused,
                    onFocusChanged = onPasswordFocusChanged,
                    passwordRequirements = passwordRequirementsList,
                    errorMessage = null
                )

                PasswordField(
                    label = "Confirm Password",
                    value = confirmPassword,
                    onValueChange = onConfirmPasswordChange,
                    onTogglePasswordVisibility = onToggleConfirmPasswordVisibility,
                    isPasswordVisible = isConfirmPasswordVisible,
                    isFocused = isConfirmPasswordFocused,
                    onFocusChanged = onConfirmPasswordFocusChanged,
                    errorMessage = if (confirmPassword.isNotEmpty() && password != confirmPassword && (isConfirmPasswordFocused || password.length >= confirmPassword.length)) {
                        "Passwords do not match"
                    } else {
                        null
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(verticalSpacing))

        TermsAndConditionsCheckbox(
            isChecked = isTermsAccepted,
            onCheckedChange = onTermsAcceptedChange,
            onTermsClick = onTermsClick,
            onPrivacyClick = onPrivacyClick,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(verticalSpacing * 1.5f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(verticalSpacing)
            ) {
                AnimatedCustomButton(
                    onClick = onSignUpClick,
                    text = "Sign up",
                    backgroundColor = MaterialTheme.colorScheme.primary,
                    textColor = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.fillMaxWidth(),
                    enabled = isSignUpEnabled
                )
            }


            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(verticalSpacing)
            ) {
                GoogleButton(
                    onClick = onGoogleSignUpClick,
                    text = "Continue with Google",
                    backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    textColor = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@Composable
private fun TermsAndConditionsCheckbox(
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    onTermsClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.padding(vertical = 8.dp)
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = onCheckedChange,
            colors = CheckboxDefaults.colors(
                checkedColor = MaterialTheme.colorScheme.primary,
                uncheckedColor = MaterialTheme.colorScheme.outline,
                checkmarkColor = MaterialTheme.colorScheme.onPrimary
            ),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "I agree to the ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Terms of Service",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onTermsClick() }
        )
        Text(
            text = " & ",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Privacy Policy",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.clickable { onPrivacyClick() }
        )
    }
}

fun isValidFullName(fullName: String): Boolean {
    val nameRegex = Regex("^[\\p{L} .'-]{2,100}$")
    return nameRegex.matches(fullName.trim())
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.length <= 254 //Email is valid
}

//password
fun hasMinLength(password: String) = password.length >= 8
fun hasUppercase(password: String) = password.any { it.isUpperCase() }
fun hasLowercase(password: String) = password.any { it.isLowerCase() }
fun hasDigit(password: String) = password.any { it.isDigit() }
fun hasSpecialChar(password: String) = password.any { "!@#\$%^&*()_+{}[]|:;\"'<>,.?/~`-=".contains(it) }

// TODO: Disable SignUp Button in the case wherein all fields is still not validated and when check box is also not checked
// TODO: Password should be shown and hidden within a certain time, password must match