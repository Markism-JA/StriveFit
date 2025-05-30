package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

@Keep
data class WorkoutPlanDto(
    @DocumentId
    var id: String? = null,
    var userId: String = "",
    var name: String = "",
    var description: String? = null,
    var isSystemGenerated: Boolean = false,

    //Meta data
    var difficultyLevel: String? = null, // Stores ExperienceLevel.SOME_VALUE.name (e.g., "BEGINNER")
    var focusAreas: List<String>? = null,      // List of FocusArea.SOME_VALUE.name (e.g., ["CHEST", "TRICEPS"])
    var workoutStyle: List<String>? = null,
    var intendedDurationMinutes: Int? = null, // Estimated duration this workout routine should take
    var requiredEquipment: List<String>? = null,

    // User interaction metadata
    var isFavorite: Boolean = false,
    var lastPerformedAt: Timestamp? = null,

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        id = null,
        userId = "",
        name = "",
        description = null,
        isSystemGenerated = false,
        difficultyLevel = null,
        focusAreas = null,
        workoutStyle = null,
        intendedDurationMinutes = null,
        requiredEquipment = null,
        isFavorite = false,
        lastPerformedAt = null,
        createdAt = null,
        updatedAt = null
    )
}