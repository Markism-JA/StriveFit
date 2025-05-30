package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.app.ExerciseEntity
import java.util.Date

@Entity(
    tableName = "logged_exercises",
    indices = [
        Index(value = ["workout_session_id", "exercise_id", "set_number"], unique = true),
        Index(value = ["workout_session_id"]),
        Index(value = ["exercise_id"]),
        Index(value = ["workout_plan_exercise_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["workout_session_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutPlanExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["workout_plan_exercise_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class LoggedExerciseEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "workout_session_id")
    val workoutSessionId: String?,

    @ColumnInfo(name = "exercise_id")
    val exerciseId: String?,

    @ColumnInfo(name = "workout_plan_exercise_id")
    val workoutPlanExerciseId: String?,

    @ColumnInfo(name = "set_number")
    val setNumber: Int,

    @ColumnInfo(name = "reps_completed")
    val repsCompleted: Int?,

    @ColumnInfo(name = "weight_kg")
    val weightKg: Double?,

    @ColumnInfo(name = "duration_seconds")
    val durationSeconds: Int?,

    @ColumnInfo(name = "distance_km")
    val distanceKm: Double?,

    @ColumnInfo(name = "calories_burned_per_set")
    val caloriesBurnedPerSet: Int?,

    @ColumnInfo(name = "actual_rpe")
    val actualRpe: Int?,

    @ColumnInfo(name = "rest_taken_seconds")
    val restTakenSeconds: Int?,

    @ColumnInfo(name = "notes")
    val notes: String?,

    @ColumnInfo(name = "logged_at")
    val loggedAt: Date?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?

)