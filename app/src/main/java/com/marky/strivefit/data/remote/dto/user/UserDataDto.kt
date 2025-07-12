package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.Timestamp

@Keep
data class UserDataDto(
    @DocumentId
    var id: String? = null,
    var email: String? = null,
    var displayName: String? = null,
    var photoUrl: String? = null,

    var dateOfBirth: Timestamp? = null,
    var sex: String? = null,
    var heightCm: Int? = null,
    var weightKg: Double? = null,

    //gamify
    var xp: Int = 0,
    var level: Int = 1,

    // App-specific preferences/info
    var experienceLevel: String? = null, // e.g., ExperienceLevel.BEGINNER.name
    var fcmToken: String? = null, // For push notifications
    var availableEquipmentDto: List<String> = emptyList(),
    var focusArea: List<String> = emptyList(),
    var setupFinished: Boolean = false,

    //Preferences
    var preferredDaysPerWeek: Int? = null,
    var preferredSessionDurationMinutes: Int? = null,
    var workoutReminderTime: String? = null,
    var defaultRestTimerSeconds: Int? = null,

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var lastLoginAt: Timestamp? = null,
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        id = null,
        email = null,
        displayName = null,
        photoUrl = null,
        dateOfBirth = null,
        sex = null,
        heightCm = null,
        weightKg = null,
        xp = 0,
        level = 1,
        experienceLevel = null,
        fcmToken = null,
        availableEquipmentDto = emptyList(),
        focusArea = emptyList(),
        setupFinished = false,
        preferredDaysPerWeek = null,
        preferredSessionDurationMinutes = null,
        workoutReminderTime = null,
        defaultRestTimerSeconds = null,
        createdAt = null,
        lastLoginAt = null,
        updatedAt = null
    )
}