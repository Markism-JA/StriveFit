package com.marky.strivefit.data.remote.repository

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.marky.strivefit.data.local.repository.AuthRepository
import com.marky.strivefit.data.local.repository.GoogleSignInResult
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : AuthRepository {

    override fun getAuthStateFlow(): Flow<FirebaseUser?> = callbackFlow {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            trySend(firebaseAuth.currentUser)
        }
        auth.addAuthStateListener(listener)
        awaitClose { auth.removeAuthStateListener(listener) }
    }

    override suspend fun signUpWithEmail(email: String, password: String): Result<FirebaseUser> {
        return runCatching {
            auth.createUserWithEmailAndPassword(email, password).await().user!!
        }
    }

    override suspend fun signInWithEmail(email: String, password: String): Result<FirebaseUser> {
        return runCatching {
            auth.signInWithEmailAndPassword(email, password).await().user!!
        }
    }

    override suspend fun signInAnonymously(): Result<FirebaseUser> {
        return runCatching {
            auth.signInAnonymously().await().user!!
        }
    }

    override suspend fun signOut(): Result<Unit> {
        return runCatching {
            auth.signOut()
        }
    }

    override fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    override suspend fun signInWithGoogle(idToken: String): Result<GoogleSignInResult> {
        return runCatching {
            val credential = GoogleAuthProvider.getCredential(idToken, null)

            val authResult: AuthResult = auth.signInWithCredential(credential).await()

            val user = authResult.user!!
            val isNewUser = authResult.additionalUserInfo?.isNewUser == true

            GoogleSignInResult(user, isNewUser)
        }
    }
}