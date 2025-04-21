package com.marky.strivefit.data.local.entities

import ExercisePlan
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "ExerciseSession",
    foreignKeys = [
        ForeignKey(
            entity = ExercisePlan::class,
            parentColumns = ["id"],
            childColumns = ["exercise_plan_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ExerciseSession (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    @ColumnInfo(name = "exercise_plan_id") val exercisePlanId: Int?,
    val date: String,
    val startTime: Long,
    val endTime: Long,
    val duration: Long,
)