package com.marky.strivefit.data.local.entities.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.app.ExerciseEntity
import java.util.Date

@Entity(
    tableName = "logged_exercises",
    indices = [
        Index(value = ["workoutSessionId", "exerciseId", "setNumber"], unique = true),
        Index(value = ["workoutSessionId"]),
        Index(value = ["exerciseId"]),
        Index(value = ["workoutPlanExerciseId"])
    ],
    foreignKeys = [
         ForeignKey(
            entity = WorkoutSessionEntity::class,
            parentColumns = ["localId"], // Use renamed PK
            childColumns = ["workoutSessionId"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["localId"], // Use renamed PK
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.Companion.CASCADE // Or RESTRICT
        ),
        ForeignKey(
            entity = WorkoutPlanExerciseEntity::class,
            parentColumns = ["id"], // Use renamed PK
            childColumns = ["workoutPlanExerciseId"],
            onDelete = ForeignKey.Companion.SET_NULL // Logged item can exist even if plan item is removed
        )
    ]
)
data class LoggedExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Renamed
    val workoutSessionId: Int,
    val exerciseId: Int,
    val workoutPlanExerciseId: Int?,
    val setNumber: Int,
    val repsCompleted: Int?,
    val weightKg: Float?,
    val durationSeconds: Int?,
    val distanceKm: Float?,
    val caloriesBurnedPerSet: Int?,
    val actualRpe: Int?,
    val restTakenSeconds: Int?,
    val notes: String?,
    val loggedAt: Date = Date()
)