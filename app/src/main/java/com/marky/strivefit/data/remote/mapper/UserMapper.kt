package com.marky.strivefit.data.remote.mapper

import com.google.firebase.Timestamp
import com.marky.strivefit.data.local.entities.user.UserDataEntity
import com.marky.strivefit.data.remote.dto.user.UserDataDto
import com.marky.strivefit.data.values.*
import java.util.Date

/**
 * Converts the Firestore DTO object to a domain/database Entity.
 * This is used after fetching data from Firestore.
 */
fun UserDataDto.toEntity(): UserDataEntity {
    // This is a critical check. The entity requires a non-null ID.
    // If the DTO's ID is null, it means something went wrong with Firestore deserialization.
    val entityId = this.id ?: throw IllegalArgumentException("User ID from DTO cannot be null")

    return UserDataEntity(
        id = entityId,
        email = this.email,
        displayName = this.displayName,
        photoUrl = this.photoUrl,
        dateOfBirth = this.dateOfBirth?.toDate(),
        sex = this.sex?.let { Sex.fromString(it) },
        heightCm = this.heightCm,
        weightKg = this.weightKg,
        xp = this.xp,
        level = this.level,
        experienceLevel = this.experienceLevel?.let { ExperienceLevel.fromString(it) },
        fcmToken = this.fcmToken,
        availableEquipment = this.availableEquipmentDto,
        focusAreas = this.focusArea.mapNotNull { FocusArea.fromString(it) },
        setupFinished = this.setupFinished,
        preferredDaysPerWeek = this.preferredDaysPerWeek,
        preferredSessionDurationMinutes = this.preferredSessionDurationMinutes,
        workoutReminderTime = this.workoutReminderTime,
        defaultRestTimerSeconds = this.defaultRestTimerSeconds,
        lastModified = this.updatedAt?.toDate(),
        lastLoginAt = this.lastLoginAt?.toDate(),
        lastSynced = Date(),
    )
}

/**
 * Converts the domain/database Entity object to a Firestore DTO.
 * This is used before writing data to Firestore.
 */
fun UserDataEntity.toDto(): UserDataDto {
    return UserDataDto(
        id = this.id,
        email = this.email,
        displayName = this.displayName,
        photoUrl = this.photoUrl,
        dateOfBirth = this.dateOfBirth?.let { Timestamp(it) }, // Convert Date? to Timestamp?
        sex = this.sex?.name, // Convert Enum? to String?
        heightCm = this.heightCm,
        weightKg = this.weightKg,
        xp = this.xp,
        level = this.level,
        experienceLevel = this.experienceLevel?.name, // Convert Enum? to String?
        fcmToken = this.fcmToken,
        availableEquipmentDto = this.availableEquipment, // Direct mapping
        focusArea = this.focusAreas.map { it.name }, // Convert List<Enum> to List<String>
        setupFinished = this.setupFinished,
        preferredDaysPerWeek = this.preferredDaysPerWeek,
        preferredSessionDurationMinutes = this.preferredSessionDurationMinutes,
        workoutReminderTime = this.workoutReminderTime,
        defaultRestTimerSeconds = this.defaultRestTimerSeconds,

        // Let Firestore handle these timestamps automatically with @ServerTimestamp
        createdAt = null,
        lastLoginAt = null,
        updatedAt = null,
    )
}