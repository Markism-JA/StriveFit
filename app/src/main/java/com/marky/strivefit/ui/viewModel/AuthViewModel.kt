package com.marky.strivefit.ui.viewModel

import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val auth: FirebaseAuth,
    private val oneTapClient: SignInClient
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    // Sign up with email, password and name (sets display name after registration)
    fun signUpWithEmail(email: String, password: String, name: String) {
        _authState.value = AuthUiState.Loading
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name)
                        .build()
                    user?.updateProfile(profileUpdates)?.addOnCompleteListener {
                        _authState.value = AuthUiState.Success
                    }
                } else {
                    val errorMsg = when (val e = task.exception) {
                        is FirebaseAuthUserCollisionException -> "Email is already in use."
                        is FirebaseAuthWeakPasswordException -> "Password is too weak."
                        is FirebaseAuthInvalidCredentialsException -> "Invalid email format."
                        else -> e?.localizedMessage ?: "Unknown error occurred."
                    }
                    _authState.value = AuthUiState.Error(errorMsg)
                }
            }
    }

    // Sign in as guest anonymously
    fun signInAsGuest() {
        _authState.value = AuthUiState.Loading
        auth.signInAnonymously()
            .addOnSuccessListener { _authState.value = AuthUiState.Success }
            .addOnFailureListener { _authState.value = AuthUiState.Error(it.message ?: "Unknown error") }
    }

    // Build Google One Tap sign-in request
    fun buildSignInRequest(webClientId: String): BeginSignInRequest {
        return BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    .setServerClientId(webClientId)
                    .setFilterByAuthorizedAccounts(false)
                    .build()
            )
            .setAutoSelectEnabled(true)
            .build()
    }

    // Firebase auth with Google ID token
    fun firebaseAuthWithGoogle(idToken: String) {
        _authState.value = AuthUiState.Loading
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { _authState.value = AuthUiState.Success }
            .addOnFailureListener { _authState.value = AuthUiState.Error(it.message ?: "Unknown error") }
    }

    // Sign out user from Firebase and One Tap client
    fun signOut() {
        auth.signOut()
        oneTapClient.signOut()
        _authState.value = AuthUiState.Idle
    }

    fun getCurrentUser() = auth.currentUser
}

sealed class AuthUiState {
    object Idle : AuthUiState()
    object Loading : AuthUiState()
    object Success : AuthUiState()
    data class Error(val message: String) : AuthUiState()
}
