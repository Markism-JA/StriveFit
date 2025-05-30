package com.marky.strivefit.data.remote.dto.user

import androidx.annotation.Keep
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp

/**
 * Represents a user's Personal Record (PR) for a specific activity and metric.
 * This DTO is designed to be flexible enough to store various types of PRs,
 * from strength exercises to running and cycling achievements.
 *
 * The combination of `activityType`, `activityNameOrId`, `metric`, and relevant `context...` fields
 * uniquely defines the nature of the PR.
 */
@Keep
data class PersonalRecordDto(
    /**
     * Firestore document ID. Can be auto-generated.
     * Consider making this a predictable composite key if you need to directly fetch
     * a PR without querying, e.g., by hashing (userId, activityType, activityNameOrId, metric, context details).
     * However, auto-generated ID + querying is simpler to start.
     */
    @DocumentId
    var id: String? = null,

    /** The ID of the user who achieved this PR. */
    var userId: String = "",

    // --- Classification of the PR ---

    /**
     * Broad category of the activity.
     * Expected values are names from your `ActivityType` enum (e.g., "EXERCISE_STRENGTH", "RUNNING").
     * @see com.marky.strivefit.data.values.ActivityType
     */
    var activityType: String = "",

    /**
     * Specific name or ID of the activity.
     * - If `activityType` relates to an exercise (e.g., "EXERCISE_STRENGTH"), this will be the `ExerciseDto.id`
     *   (e.g., "squat_barbell", "plank").
     * - If `activityType` is "RUNNING" or "CYCLING", this will be a predefined constant identifier
     *   (e.g., from `PRActivityName.RUN_5KM`, `PRActivityName.CYCLE_GENERIC`).
     */
    var activityNameOrId: String = "",

    // --- The Core Metric of the PR ---

    /**
     * The specific metric being recorded (e.g., "WEIGHT", "REPS", "DURATION", "DISTANCE").
     * Expected values are names from your `PRMetric` enum.
     * @see com.marky.strivefit.data.values.PRMetric
     */
    var metric: String = "",

    /** The numerical value of the personal record. */
    var value: Double = 0.0,

    /**
     * The unit for the `value` field.
     * E.g., "kg", "lbs", "reps", "seconds", "minutes", "meters", "km", "miles", "watts".
     * Be consistent with the units you use.
     */
    var unit: String = "",

    // --- Context Fields: Provide specifics for certain metric types ---

    /**
     * Context: For PRs where `metric` is "REPS", this stores the weight at which those reps were achieved.
     * For bodyweight exercises, this might be null or 0.
     */
    var contextWeight: Double? = null,

    /**
     * Context: Unit for `contextWeight` (e.g., "kg", "lbs").
     * Should be present if `contextWeight` is not null.
     */
    var contextWeightUnit: String? = null,

    /**
     * Context: For PRs where `metric` is "DURATION" (representing fastest time),
     * this stores the distance over which that time was achieved.
     */
    var contextDistance: Double? = null,

    /**
     * Context: Unit for `contextDistance` (e.g., "meters", "km", "miles").
     * Should be present if `contextDistance` is not null.
     */
    var contextDistanceUnit: String? = null,

    /**
     * Context:
     * - For PRs where `metric` is "DISTANCE", this can optionally store the duration it took to cover that distance.
     * - For PRs where `metric` is "WEIGHT" or another rate-based measure (like average power),
     *   this can store the duration over which that average was sustained (e.g., 20-minute FTP test).
     */
    var contextDurationSeconds: Int? = null,


    // --- Record-Keeping & Provenance ---

    /** Timestamp of when this PR was actually achieved (derived from the source activity). */
    var achievedAt: Timestamp? = null,

    /**
     * ID of the source session document (e.g., `WorkoutSessionDto.id`, `RunningSessionDto.id`)
     * from which this PR was derived. Helps trace back to the original activity.
     */
    var sourceSessionId: String? = null,

    /**
     * If the PR was derived from a specific logged exercise within a workout session,
     * this is the ID of that `LoggedExerciseDto.id`.
     */
    var sourceLoggedExerciseId: String? = null,

    /** Timestamp of when this PR document was first created in Firestore. */
    @ServerTimestamp
    var createdAt: Timestamp? = null,

    /** Timestamp of when this PR document was last updated in Firestore. */
    @ServerTimestamp
    var updatedAt: Timestamp? = null
) {
    constructor() : this(
        id = null,
        userId = "",
        activityType = "",
        activityNameOrId = "",
        metric = "",
        value = 0.0,
        unit = "",
        contextWeight = null,
        contextWeightUnit = null,
        contextDistance = null,
        contextDistanceUnit = null,
        contextDurationSeconds = null,
        achievedAt = null,
        sourceSessionId = null,
        sourceLoggedExerciseId = null,
        createdAt = null,
        updatedAt = null
    )
}