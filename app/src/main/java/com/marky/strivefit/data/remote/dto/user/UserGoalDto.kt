package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

// Assuming GoalType and RepeatInterval are enums in your 'values' directory
// e.g., com.yourpackage.data.values.GoalType

@Keep
data class UserGoalDto(
    @DocumentId
    var id: String? = null, // Firestore document ID for this goal

    var userId: String = "", // User who owns this goal

    var title: String = "", // A user-friendly title for the goal (e.g., "Lose 5kg", "Run 5k in 25 mins")

    // Goal specifics
    var goalType: String = "",      // Stores GoalType.SOME_VALUE.name (e.g., "WEIGHT_LOSS", "IMPROVE_STRENGTH")
    var targetValue: Double = 0.0,  // Use Double for flexibility (e.g., target weight 75.5 kg, target reps 10.0)
    var currentValue: Double = 0.0, // Current progress towards the target
    var unit: String? = null,       // Optional: Unit for target/current value (e.g., "kg", "km", "reps", "minutes", "seconds")

    var exerciseId: String? = null, // Optional: ID of the ExerciseDto (String) if goal is exercise-specific

    // Timing and Status
    var startDate: Timestamp? = null,     // When the goal period starts
    var targetEndDate: Timestamp? = null, // Target completion date
    var isCompleted: Boolean = false,
    var completedDate: Timestamp? = null, // When the goal was marked as completed

    // Recurrence (if applicable)
    var targetFrequency: Int? = null,       // e.g., "3 times" (how often the user aims to work on this goal)
    var repeatInterval: String? = null,     // Stores RepeatInterval.SOME_VALUE.name (e.g., "WEEKLY", "MONTHLY")
    // Nullable if it's a one-time goal or `targetFrequency` is not set
    var repeatDays: List<String>? = null,   // If weekly: ["MONDAY", "WEDNESDAY"]. If monthly: ["1", "15"] (day numbers).

    var notes: String? = null,              // Any additional notes from the user about the goal

    @ServerTimestamp
    var createdAt: Timestamp? = null,       // When this goal document was created
    @ServerTimestamp
    var updatedAt: Timestamp? = null        // When this goal document was last updated
) {
    // No-argument constructor for Firestore
    constructor() : this(
        id = null, userId = "", title = "", goalType = "", targetValue = 0.0, currentValue = 0.0, unit = null,
        exerciseId = null, startDate = null, targetEndDate = null, isCompleted = false, completedDate = null,
        targetFrequency = null, repeatInterval = null, repeatDays = null, notes = null,
        createdAt = null, updatedAt = null
    )
}