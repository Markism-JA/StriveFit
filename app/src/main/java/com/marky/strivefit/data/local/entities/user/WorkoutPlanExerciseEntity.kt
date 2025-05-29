package com.marky.strivefit.data.local.entities.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.app.ExerciseEntity

@Entity(
    tableName = "workout_plan_exercises",
    indices = [
        Index(value = ["workoutPlanId", "exerciseOrder"], unique = true),
        Index(value = ["workoutPlanId"]),
        Index(value = ["exerciseId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutPlanEntity::class,
            parentColumns = ["id"], // Use renamed PK
            childColumns = ["workoutPlanId"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"], // Use renamed PK
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class WorkoutPlanExerciseEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val workoutPlanId: Int,
    val exerciseId: Int,
    val exerciseOrder: Int, //reps based
    val sets: Int?, //reps based
    val repsMin: Int?, // can allow range for flexibility
    val repsMax: Int?, // however can be set to just the same
    val targetRpe: Int?, // Rate of Perceived Exertion (RPE) 1-10 how hard should the exercise feel
    val durationSeconds: Int?, //time base
    val restPeriodSeconds: Int?, //
    val notes: String?
)