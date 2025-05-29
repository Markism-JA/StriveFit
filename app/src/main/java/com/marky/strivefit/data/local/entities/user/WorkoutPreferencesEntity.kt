package com.marky.strivefit.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.user.UserDataEntity
import java.time.LocalTime

@Entity(
    tableName = "workout_preferences",
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class WorkoutPreferencesEntity(
    @PrimaryKey val userId: String,
    val daysPerWeek: Int, //2-7days
    val sessionDurationMinutes: Int, //15to90m
    val workoutReminder: LocalTime
)