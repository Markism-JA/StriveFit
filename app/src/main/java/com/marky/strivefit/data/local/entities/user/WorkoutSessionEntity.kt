package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.WorkoutSessionStatus
import java.util.Date
@Entity(
    tableName = "workout_sessions",
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["workout_plan_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutPlanEntity::class,
            parentColumns = ["id"],
            childColumns = ["workout_plan_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class WorkoutSessionEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "workout_plan_id")
    val workoutPlanId: String?,

    @ColumnInfo(name = "workout_plan_name_snapshot")
    var workoutPlanNameSnapshot: String?,

    @ColumnInfo(name = "session_name")
    val sessionName: String?,

    @ColumnInfo(name = "start_time")
    val startTime: Date?,

    @ColumnInfo(name = "end_time")
    var endTime: Date?,

    @ColumnInfo(name = "actual_duration_seconds")
    var actualDurationSeconds: Int?,

    @ColumnInfo(name = "status")
    var status: WorkoutSessionStatus,

    @ColumnInfo(name = "user_mood_rating")
    var userMoodRating: Int?,

    @ColumnInfo(name = "perceived_session_difficulty_rpe")
    var perceivedSessionDifficultyRPE: Int?,

    @ColumnInfo(name = "session_focus_rating")
    var sessionFocusRating: Int?,

    @ColumnInfo(name = "total_volume")
    var totalVolume: Double?,

    @ColumnInfo(name = "total_sets_completed")
    var totalSetsCompleted: Int?,

    @ColumnInfo(name = "total_reps_completed")
    var totalRepsCompleted: Int?,

    @ColumnInfo(name = "total_exercises_completed")
    var totalExercisesCompleted: Int?,

    @ColumnInfo(name = "estimated_calories_burned")
    var estimatedCaloriesBurned: Double?,

    @ColumnInfo(name = "notes")
    var notes: String?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?
)