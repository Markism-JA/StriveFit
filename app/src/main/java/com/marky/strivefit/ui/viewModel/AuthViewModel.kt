package com.marky.strivefit.ui.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.*
import com.marky.strivefit.data.local.repository.AuthRepository
import com.marky.strivefit.data.local.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

@Suppress("DEPRECATION")
@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthUiState>(AuthUiState.Idle)
    val authState: StateFlow<AuthUiState> = _authState.asStateFlow()

    val isSignedIn: StateFlow<Boolean> = authRepository.getAuthStateFlow()
        .map { firebaseUser -> firebaseUser != null }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = authRepository.getCurrentUser() != null
        )

    fun signInWithEmailAndPassword(email: String, pass: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            try {
                val result = authRepository.signInWithEmail(email, pass).getOrThrow()
                _authState.value = AuthUiState.Success(result.uid)
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun signInWithGoogle(idToken: String) {
        viewModelScope.launch {
            _authState.value = AuthUiState.Loading
            try {
                val result = authRepository.signInWithGoogle(idToken).getOrThrow()
                _authState.value = AuthUiState.Success(result.user.uid)
            } catch (e: Exception) {
                _authState.value = AuthUiState.Error(e.message ?: "An unknown error occurred")
            }
        }
    }

    fun resetAuthStateToIdle() {
        _authState.value = AuthUiState.Idle
    }

    fun getCurrentUser(): FirebaseUser? = authRepository.getCurrentUser()

    fun signOut() {
        viewModelScope.launch {
            authRepository.signOut()
        }
    }
}