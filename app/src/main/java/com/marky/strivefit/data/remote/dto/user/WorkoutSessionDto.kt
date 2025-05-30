package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

@Keep
data class WorkoutSessionDto(
    @DocumentId
    var id: String? = null, // Firestore document ID for this specific workout session instance

    var userId: String = "", // ID of the user who performed this session

    // --- Source of the Workout (Optional) ---
    var workoutPlanId: String? = null,
    var workoutPlanNameSnapshot: String? = null,

    // --- Session Details ---
    var sessionName: String? = null,   // User-defined name for the session (e.g., "Morning Chest Blast")
    // or derived from the workoutPlanNameSnapshot.

    var startTime: Timestamp? = null,  // Actual start time of the workout session
    var endTime: Timestamp? = null,    // Actual end time of the workout session
    var actualDurationSeconds: Int? = null, // Total duration of the session in seconds.
    // Can be calculated (endTime - startTime) or explicitly tracked if pauses are excluded.

    /**
     * Status of the workout session.
     * Expected values are names from your `WorkoutSessionStatus` enum
     * (e.g., "STARTED", "IN_PROGRESS", "PAUSED", "COMPLETED", "CANCELLED").
     * @see com.marky.strivefit.data.values.WorkoutSessionStatus
     */
    var status: String = "",


    var userMoodRating: Int? = null,              // e.g., 1-5 scale representing user's feeling post-workout
    var perceivedSessionDifficultyRPE: Int? = null, // Overall session RPE (Rating of Perceived Exertion), e.g., 1-10
    var sessionFocusRating: Int? = null,
    var totalVolume: Double? = null,
    var totalSetsCompleted: Int? = null,
    var totalRepsCompleted: Int? = null,
    var totalExercisesCompleted: Int? = null,
    var estimatedCaloriesBurned: Double? = null,

    var notes: String? = null,

    @ServerTimestamp
    var createdAt: Timestamp? = null,
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        id = null,
        userId = "",
        workoutPlanId = null,
        workoutPlanNameSnapshot = null,
        sessionName = null,
        startTime = null,
        endTime = null,
        actualDurationSeconds = null,
        status = "",
        userMoodRating = null,
        perceivedSessionDifficultyRPE = null,
        sessionFocusRating = null,
        totalVolume = null,
        totalSetsCompleted = null,
        totalRepsCompleted = null,
        totalExercisesCompleted = null,
        estimatedCaloriesBurned = null,
        notes = null,
        createdAt = null,
        updatedAt = null
    )
}