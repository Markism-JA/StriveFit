package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.app.ExerciseEntity
import java.util.Date

@Entity(
    tableName = "workout_plan_exercises",
    indices = [
        Index(value = ["workout_plan_id", "exercise_order"], unique = true),
        Index(value = ["workout_plan_id"]),
        Index(value = ["exercise_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutPlanEntity::class,
            parentColumns = ["id"],
            childColumns = ["workout_plan_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutPlanExerciseEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "workout_plan_id")
    val workoutPlanId: String,

    @ColumnInfo(name = "exercise_id")
    val exerciseId: String,

    @ColumnInfo(name = "exercise_order")
    val exerciseOrder: Int,

    @ColumnInfo(name = "sets")
    val sets: Int?,

    @ColumnInfo(name = "reps_min")
    val repsMin: Int?,

    @ColumnInfo(name = "reps_max")
    val repsMax: Int?,

    @ColumnInfo(name = "target_rpe")
    val targetRpe: Int?,

    @ColumnInfo(name = "fixed_weight_kg")
    val fixedWeightKg: Double?,

    @ColumnInfo(name = "duration_seconds")
    val durationSeconds: Int?,

    @ColumnInfo(name = "rest_between_sets_seconds")
    val restBetweenSetsSeconds: Int?,

    @ColumnInfo(name = "notes")
    val notes: String?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?
)