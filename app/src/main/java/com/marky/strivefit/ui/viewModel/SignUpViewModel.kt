package com.marky.strivefit.ui.viewModel

import UserProfileUpdater
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseAuthWeakPasswordException
import com.marky.strivefit.data.local.repository.AuthRepository
import com.marky.strivefit.data.local.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


data class PasswordFieldRequirementStatus(
    val message: String,
    val isSatisfied: Boolean
)

data class SignUpUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isTermsAccepted: Boolean = false,
    val isPasswordVisible: Boolean = false,
    val isConfirmPasswordVisible: Boolean = false,

    val passwordRequirements: List<PasswordFieldRequirementStatus> = emptyList(),

    val hasFullNameBeenFocused: Boolean = false,
    val hasEmailBeenFocused: Boolean = false,
    val hasConfirmPasswordFocused: Boolean = false,
    val hasPasswordFocused: Boolean = false,

    val fullNameError: String? = null,
    val emailError: String? = null,
    val confirmPasswordError: String? = null,



    val isLoading: Boolean = false
) {
    val isSignUpButtonEnabled: Boolean
        get() = isValidFullName(fullName) && isValidEmail(email) &&
                passwordRequirements.all {it.isSatisfied} && password == confirmPassword &&
                isTermsAccepted && !isLoading
}


fun isValidFullName(fullName: String): Boolean {
    val nameRegex = Regex("^[\\p{L} .'-]{2,100}$")
    return nameRegex.matches(fullName.trim())
}

fun isValidEmail(email: String): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(email.trim()).matches() && email.trim().length <= 254
}

fun isPasswordStrongEnough(password: String): Boolean {
    return hasMinLength(password) && hasUppercase(password) && hasLowercase(password) && hasDigit(password) && hasSpecialChar(password)
}

fun hasMinLength(password: String) = password.length >= 8
fun hasUppercase(password: String) = password.any { it.isUpperCase() }
fun hasLowercase(password: String) = password.any { it.isLowerCase() }
fun hasDigit(password: String) = password.any { it.isDigit() }
fun hasSpecialChar(password: String) = password.any { "!@#\$%^&*()_+{}[]|:;\"'<>,.?/~`-=".contains(it) }

sealed interface SignUpEvent {
    data class FullNameChanged(val value: String) : SignUpEvent
    data class EmailChanged(val value: String) : SignUpEvent
    data class PasswordChanged(val value: String) : SignUpEvent
    data class ConfirmPasswordChanged(val value: String) : SignUpEvent
    data class TermsAcceptedChanged(val value: Boolean) : SignUpEvent
    object FullNameFocusLost : SignUpEvent
    object EmailFocusLost : SignUpEvent
    object PasswordFocusLost : SignUpEvent
    object ConfirmPasswordFocusLost : SignUpEvent
    object TogglePasswordVisibility : SignUpEvent
    object ToggleConfirmPasswordVisibility : SignUpEvent
    object SignUpClicked : SignUpEvent
    object GoogleSignUpClicked : SignUpEvent
    data class GoogleSignInSucceeded(val idToken: String) : SignUpEvent
}

sealed interface SignUpResult {
    object Success : SignUpResult
    data class Error(val message: String) : SignUpResult
    object LaunchGoogleSignIn : SignUpResult
}

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val userProfileUpdater: UserProfileUpdater
) : ViewModel() {

    companion object {
        const val PASSWORD_VISIBLE_DURATION_MS = 3000L
        const val MAX_PASSWORD_LENGTH = 64
        const val MAX_FULL_NAME_LENGTH = 100
        const val MAX_EMAIL_LENGTH = 254
    }

    private val _uiState = MutableStateFlow(SignUpUiState())
    val uiState: StateFlow<SignUpUiState> = _uiState.asStateFlow()

    private val _resultFlow = MutableSharedFlow<SignUpResult>()
    val resultFlow = _resultFlow.asSharedFlow()

    private var passwordVisibilityJob: Job? = null
    private var confirmPasswordVisibilityJob: Job? = null

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.FullNameChanged ->
                _uiState.update { it.copy(fullName = event.value.take(MAX_FULL_NAME_LENGTH)) }
            is SignUpEvent.EmailChanged ->
                _uiState.update { it.copy(email = event.value.take(MAX_EMAIL_LENGTH)) }

            is SignUpEvent.PasswordChanged -> handlePasswordChange(event.value.take(MAX_PASSWORD_LENGTH))
            is SignUpEvent.ConfirmPasswordChanged -> handleConfirmPasswordChange(event.value.take(MAX_PASSWORD_LENGTH))

            is SignUpEvent.TermsAcceptedChanged -> _uiState.update { it.copy(isTermsAccepted = event.value) }
            SignUpEvent.SignUpClicked -> performEmailSignUp()

            SignUpEvent.TogglePasswordVisibility -> handleTogglePasswordVisibility()
            SignUpEvent.ToggleConfirmPasswordVisibility -> handleToggleConfirmPasswordVisibility()
            SignUpEvent.FullNameFocusLost -> {
                _uiState.update {
                    val error = if (!isValidFullName(it.fullName)) "Please enter a valid full name." else null
                    it.copy(fullNameError = error, hasFullNameBeenFocused = true)
                }
            }
            SignUpEvent.EmailFocusLost -> {
                _uiState.update {
                    val error = if (!isValidEmail(it.email)) "Please enter a valid email address." else null
                    it.copy(emailError = error, hasEmailBeenFocused = true)
                }
            }
            SignUpEvent.PasswordFocusLost -> _uiState.update { it.copy(hasPasswordFocused = true) }
            SignUpEvent.ConfirmPasswordFocusLost -> _uiState.update { it.copy(hasConfirmPasswordFocused = true) }
            SignUpEvent.GoogleSignUpClicked -> viewModelScope.launch { _resultFlow.emit(SignUpResult.LaunchGoogleSignIn) }
            is SignUpEvent.GoogleSignInSucceeded -> performGoogleSignIn(event.idToken)
        }
    }



    private fun handleTogglePasswordVisibility() {
        passwordVisibilityJob?.cancel()

        val currentState = _uiState.value
        if (currentState.isPasswordVisible) {
            // If it's already visible, just hide it.
            _uiState.update { it.copy(isPasswordVisible = false) }
        } else {
            // If it's hidden, show it and start a timer to hide it again.
            passwordVisibilityJob = viewModelScope.launch {
                _uiState.update { it.copy(isPasswordVisible = true) }
                delay(PASSWORD_VISIBLE_DURATION_MS)
                _uiState.update { it.copy(isPasswordVisible = false) }
            }
        }
    }

    private fun handleToggleConfirmPasswordVisibility() {
        confirmPasswordVisibilityJob?.cancel()
        val currentState = _uiState.value
        if (currentState.isConfirmPasswordVisible) {
            _uiState.update { it.copy(isConfirmPasswordVisible = false) }
        } else {
            confirmPasswordVisibilityJob = viewModelScope.launch {
                _uiState.update { it.copy(isConfirmPasswordVisible = true) }
                delay(PASSWORD_VISIBLE_DURATION_MS)
                _uiState.update { it.copy(isConfirmPasswordVisible = false) }
            }
        }
    }

    private fun generatePasswordRequirementStatus(password: String): List<PasswordFieldRequirementStatus> {
        return listOf(
            PasswordFieldRequirementStatus(
                message = "Minimum 8 characters",
                isSatisfied = hasMinLength(password)
            ),
            PasswordFieldRequirementStatus(
                message = "1 uppercase",
                isSatisfied = hasUppercase(password)
            ),
            PasswordFieldRequirementStatus(
                message = "1 lowercase",
                isSatisfied = hasLowercase(password)
            ),
            PasswordFieldRequirementStatus(
                message = "1 digit",
                isSatisfied = hasDigit(password)
            ),
            PasswordFieldRequirementStatus(
                message = "1 special char",
                isSatisfied = hasSpecialChar(password)
            )
        )
    }

    private fun handlePasswordChange(password: String) {
        val requirements = generatePasswordRequirementStatus(password)
        val confirmPassword = _uiState.value.confirmPassword
        val confirmPasswordError = if (confirmPassword.isNotEmpty() && password != confirmPassword) {
            "Passwords do not match."
        } else {
            null
        }

        _uiState.update {
            it.copy(
                password = password,
                passwordRequirements = requirements,
                confirmPasswordError = confirmPasswordError
            )
        }
    }

    private fun handleConfirmPasswordChange(confirmPassword: String) {
        val currentPassword = _uiState.value.password
        val error = if (currentPassword != confirmPassword) {
            "Passwords do not match."
        } else {
            null
        }

        _uiState.update {
            it.copy(
                confirmPassword = confirmPassword,
                confirmPasswordError = error
            )
        }
    }


    private fun performEmailSignUp() {
        val state = _uiState.value
        if (!state.isSignUpButtonEnabled) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            try {
                val user = authRepository.signUpWithEmail(state.email.trim(), state.password)
                    .getOrThrow()

                userProfileUpdater.updateDisplayName(user, state.fullName.trim()).getOrThrow()

                userRepository.createUserProfileEmail(user.uid, user.email.toString(), state.fullName.trim())
                    .getOrThrow()

                _resultFlow.emit(SignUpResult.Success)

            } catch (e: Exception) {
                val errorMsg = when (e) {
                    is FirebaseAuthUserCollisionException -> "Email is already in use."
                    is FirebaseAuthWeakPasswordException -> "Password is too weak."
                    else -> e.localizedMessage ?: "Sign up failed."
                }
                _resultFlow.emit(SignUpResult.Error(errorMsg))
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    private fun performGoogleSignIn(idToken: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            try {
                // 1. Authenticate with Google via the repository
                val signInResult = authRepository.signInWithGoogle(idToken).getOrThrow()

                // 2. Check if it's a new user. This is the key logic.
                if (signInResult.isNewUser) {
                    // It's a new user, so create their data profile in Firestore/Room.
                    userRepository.createGoogleProfile( // Assuming a dedicated method for this
                        userId = signInResult.user.uid,
                        email = signInResult.user.email,
                        displayName = signInResult.user.displayName,
                        photoUrl = signInResult.user.photoUrl?.toString()
                    ).getOrThrow()
                } else {
                    // It's a returning user. We don't need to create a profile,
                    // but we should refresh the local data to ensure it's up-to-date.
                    userRepository.refreshUser(signInResult.user.uid).getOrThrow()
                }

                // 3. All steps succeeded, emit the success result.
                _resultFlow.emit(SignUpResult.Success)

            } catch (e: Exception) {
                // If any step failed, emit an error.
                _resultFlow.emit(SignUpResult.Error(e.localizedMessage ?: "Google Sign-In failed."))
            } finally {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }
}