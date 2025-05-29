package com.marky.strivefit.data.local.entities.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.app.ExerciseEntity
import com.marky.strivefit.data.local.entities.app.WorkoutPlanExerciseEntity
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
            parentColumns = ["localId"], // Use renamed PK
            childColumns = ["workoutPlanExerciseId"],
            onDelete = ForeignKey.Companion.SET_NULL // Logged item can exist even if plan item is removed
        )
    ]
)
data class LoggedExerciseEntity(
    @PrimaryKey(autoGenerate = true) val localId: Int = 0, // Renamed
    val workoutSessionId: Int,
    val exerciseId: Int,
    val workoutPlanExerciseId: Int?,
    val setNumber: Int,
    var repsCompleted: Int?,
    var weightKg: Float?,
    var durationSeconds: Int?,
    var distanceKm: Float?,
    var caloriesBurnedPerSet: Int?,
    var actualRpe: Int?,
    var restTakenSeconds: Int?,
    var notes: String?,
    val loggedAt: Date = Date()
)