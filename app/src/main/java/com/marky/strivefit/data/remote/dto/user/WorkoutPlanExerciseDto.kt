package com.marky.strivefit.data.remote.dto.user // Adjust package as needed

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

@Keep
data class WorkoutPlanExerciseDto(
    @DocumentId
    var id: String? = null,

    var workoutPlanId: String = "",      // ID of the parent WorkoutPlanDto (String)
    var exerciseId: String = "", // ID of the main ExerciseDto (String) from your 'app' DTOs

    var exerciseOrder: Int = 0,        // Order of this exercise within the plan (0-indexed or 1-indexed)

    var sets: Int? = null,
    var repsMin: Int? = null,
    var repsMax: Int? = null,
    var targetRpe: Int? = null,
    var fixedWeightKg: Double? = null, // Optional: If a specific weight is prescribed by AI/user
    var durationSeconds: Int? = null,  // For timed exercises (e.g., plank) or timed sets
    var restBetweenSetsSeconds: Int? = null, // Prescribed rest *between sets* of this specific exercise

    var notes: String? = null,         // Specific instructions or notes for this exercise in this plan

     var addedToPlanAt: Timestamp? = null,


    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        id = null,
        workoutPlanId = "",
        exerciseId = "",
        exerciseOrder = 0,
        sets = null,
        repsMin = null,
        repsMax = null,
        targetRpe = null,
        fixedWeightKg = null,
        durationSeconds = null,
        restBetweenSetsSeconds = null,
        notes = null,
        addedToPlanAt = null
    )
}