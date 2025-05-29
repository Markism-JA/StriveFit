package com.marky.strivefit.data.local.entities.user

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.user.UserDataEntity
import com.marky.strivefit.data.local.entities.user.WorkoutPlanEntity
import com.marky.strivefit.data.values.WorkoutSessionStatus
import java.util.Date

@Entity(
    tableName = "workout_sessions",
    indices = [
        Index(value = ["userId"]),
        Index(value = ["workoutPlanId"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = WorkoutPlanEntity::class,
            parentColumns = ["id"], // Use renamed PK
            childColumns = ["workoutPlanId"],
            onDelete = ForeignKey.Companion.SET_NULL // A session can exist even if plan is deleted
        )
    ]
)
data class WorkoutSessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0, // Renamed
    val userId: String, // UUID
    val workoutPlanId: Int?,
    val sessionName: String?,
    val startTime: Date,
    var endTime: Date?,
    var status: WorkoutSessionStatus = WorkoutSessionStatus.STARTED,
    var mood: Int?, //1-5 whether they like each exercise
    var perceivedExertion: Int?,
    var totalVolume: Float?, //total weight lifted sets x reps x weight
    var totalCaloriesBurned: Double = 0.0,
    var notes: String?,
    val createdAt: Date = Date(),
    var updatedAt: Date = Date()
)