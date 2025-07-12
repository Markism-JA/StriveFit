package com.marky.strivefit.data.remote.repository

import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.marky.strivefit.data.local.dao.user.UserDataDao
import com.marky.strivefit.data.remote.mapper.toDto
import com.marky.strivefit.data.remote.mapper.toEntity
import com.marky.strivefit.data.remote.dto.user.UserDataDto
import com.marky.strivefit.data.local.entities.user.UserDataEntity
import com.marky.strivefit.data.local.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userDao: UserDataDao // Injected by Hilt defined via dependency injection
) : UserRepository {

    private val usersCollection = Firebase.firestore.collection("users")
    private val firebaseAuth = Firebase.auth

    override fun getUser(userId: String): Flow<UserDataEntity?> {
        // 1. Immediately return the Flow from the local Room database.
        //    The UI gets data right away.
        return userDao.getUserById(userId)

        // Note: A more advanced version would trigger a refresh here automatically.
        // For simplicity, we'll keep the refresh manual for now via the refreshUser function.
    }

    override suspend fun refreshUser(userId: String): Result<Unit> {
        return runCatching{
            // 2. In the background, fetch the latest data from Firebase.
            val snapshot = usersCollection.document(userId).get().await()
            val userDto = snapshot.toObject<UserDataDto>()

            if (userDto != null) {
                userDao.upsertUser(userDto.toEntity())
                Result.success(Unit)
            } else {
                val currentUser = firebaseAuth.currentUser
                if (currentUser != null) {
                    createUserProfileEmail(
                        userId = currentUser.uid,
                        email = currentUser.email.toString(),
                        displayName = currentUser.displayName.toString(),
                    )
                } else {
                    throw IllegalStateException("Attempted to refresh a mismatched or unauthenticated user.")
                }
            }
        }
    }


    override suspend fun createUserProfileEmail(userId: String, displayName: String, email: String): Result<Unit> {
        return try {
            val newUserEntity = UserDataEntity(
                id = userId,
                email = email,
                displayName = displayName,
                photoUrl = null,
                dateOfBirth = null,
                sex = null,
                heightCm = null,
                weightKg = null,
                experienceLevel = null,
                fcmToken = null,
                availableEquipment = emptyList(),
                focusAreas = emptyList(),
                xp = 0,
                level = 1,
                setupFinished = false,
                preferredDaysPerWeek = 3,
                preferredSessionDurationMinutes = 45,
                workoutReminderTime = null,
                defaultRestTimerSeconds = 60,
                lastModified = null,
                lastSynced = null,
                lastLoginAt = null
            )
            updateUser(newUserEntity)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun createGuestProfile(userId: String, displayName: String): Result<Unit> {
        return runCatching {
            val extinguishUser = userDao.getUserById(userId)
            if (extinguishUser != null) {
                throw IllegalStateException("User already exists")
            }

            val newUserEntity = UserDataEntity(
                id = userId,
                email = null,
                displayName = displayName,
                photoUrl = null,
                dateOfBirth = null,
                sex = null,
                heightCm = null,
                weightKg = null,
                experienceLevel = null,
                fcmToken = null,
                availableEquipment = emptyList(),
                focusAreas = emptyList(),
                xp = 0,
                level = 1,
                setupFinished = false,
                preferredDaysPerWeek = 3,
                preferredSessionDurationMinutes = 45,
                workoutReminderTime = null,
                defaultRestTimerSeconds = 60,
                lastModified = null,
                lastSynced = null,
                lastLoginAt = null
            )
            updateUser(newUserEntity)
            Result.success(Unit)
        }
    }

    override suspend fun createGoogleProfile(
        userId: String,
        displayName: String?,
        email: String?,
        photoUrl: String?
    ): Result<Unit> {
       return runCatching {
           val newUserEntity = UserDataEntity(
               id = userId,
               email = email,
               displayName = displayName,
               photoUrl = photoUrl,
               dateOfBirth = null,
               sex = null,
               heightCm = null,
               weightKg = null,
               experienceLevel = null,
               fcmToken = null,
               availableEquipment = emptyList(),
               focusAreas = emptyList(),
               xp = 0,
               level = 1,
               setupFinished = false,
               preferredDaysPerWeek = 3,
               preferredSessionDurationMinutes = 45,
               workoutReminderTime = null,
               defaultRestTimerSeconds = 60,
               lastModified = null,
               lastSynced = null,
               lastLoginAt = null
           )
           updateUser(newUserEntity)
           Result.success(Unit)
       }
    }

    override suspend fun updateUser(user: UserDataEntity): Result<Unit> {
        return try {
            // 1. Optimistic Update: Save to Room immediately. UI updates instantly.
            userDao.upsertUser(user)

            // 2. Convert the updated entity to a DTO for Firebase.
            val userDto = user.toDto()

            // 3. Push the update to Firebase.
            usersCollection.document(user.id).set(userDto, SetOptions.merge()).await()

            Result.success(Unit)
        } catch (e: Exception) {
            // If Firebase fails, the data is still saved locally.
            // TODO: Logic to "needs sync".
            Result.failure(e)
        }
    }
}