package com.marky.strivefit.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.GoalType
import com.marky.strivefit.data.values.RepeatInterval

@Entity(
    tableName = "user_goals",
    primaryKeys = ["userId"],
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.Companion.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exerciseId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class UserGoalEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val userId: String,
    val exerciseId: Int?,
    val goalType: GoalType,
    val targetValue: Int,
    val currentValue: Int = 0,
    val startDate: Long,
    val endDate: Long?,
    val isCompleted: Boolean = false,

    // Repeat Logic
    val targetFrequency: Int = 1,
    val repeatInterval: RepeatInterval = RepeatInterval.NONE,
    val repeatDays: String? = null
)