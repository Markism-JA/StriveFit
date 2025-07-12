package com.marky.strivefit.data.local.repository

import com.marky.strivefit.data.local.entities.user.UserDataEntity
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getUser(userId: String): Flow<UserDataEntity?>
    suspend fun updateUser(user: UserDataEntity): Result<Unit>
    suspend fun refreshUser(userId: String): Result<Unit>
    suspend fun createUserProfileEmail(userId: String, displayName: String, email: String): Result<Unit>
    suspend fun createGuestProfile(userId: String, displayName: String): Result<Unit>
    suspend fun createGoogleProfile(userId: String, displayName: String?, email: String?, photoUrl: String?): Result<Unit>
}
