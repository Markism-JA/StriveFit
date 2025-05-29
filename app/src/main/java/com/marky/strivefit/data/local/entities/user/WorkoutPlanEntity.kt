package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.user.UserDataEntity
import com.marky.strivefit.data.values.ExperienceLevel
import java.util.Date

@Entity(
    tableName = "workout_plans",
    indices = [Index(value = ["user_id", "name"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["uuid"],
            childColumns = ["userId"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class WorkoutPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "userId") val userId: String,
    @ColumnInfo(name = "name") val name: String,
    val description: String,
    val isPublic: Boolean,
    val difficultyLevel: ExperienceLevel?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()
)