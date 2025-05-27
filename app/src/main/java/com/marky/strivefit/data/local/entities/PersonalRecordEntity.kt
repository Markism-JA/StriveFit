package com.marky.strivefit.data.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.PRMetric
import java.util.Date

@Entity(
    tableName = "personal_records",
    indices = [
        Index(value = ["userId", "exerciseId", "metric"], unique = true), // only one PR per metric per exercise per user
        Index(value = ["loggedExerciseId"]),
        Index(value = ["exerciseId"]),
    ],
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
        ),
        ForeignKey(
            entity = LoggedExerciseEntity::class,
            parentColumns = ["localId"],
            childColumns = ["loggedExerciseId"],
            onDelete = ForeignKey.Companion.SET_NULL // Optional: preserve PR even if logged exercise is deleted
        )
    ]
)
data class PersonalRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val userId: String, // FK → UserDataEntity(uuid)
    val exerciseId: Int, // FK → ExerciseEntity(id)
    val metric: PRMetric, // Enum: WEIGHT, REPS, etc.
    val value: Float, // actual PR value

    val loggedExerciseId: Int?, // nullable FK → LoggedExerciseEntity(localId)

    val achievedAt: Date
)