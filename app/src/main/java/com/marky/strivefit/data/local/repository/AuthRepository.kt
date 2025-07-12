package com.marky.strivefit.data.local.repository

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun getAuthStateFlow(): Flow<FirebaseUser?>
    suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser>
    suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser>
    suspend fun signInAnonymously(): Result<FirebaseUser>
    suspend fun signOut(): Result<Unit>
    fun getCurrentUser(): FirebaseUser?
    suspend fun signInWithGoogle(idToken: String): Result<GoogleSignInResult>
}

data class GoogleSignInResult(
    val user: FirebaseUser,
    val isNewUser: Boolean
)