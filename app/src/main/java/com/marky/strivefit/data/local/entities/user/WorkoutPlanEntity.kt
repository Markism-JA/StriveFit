package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.Equipments
import com.marky.strivefit.data.values.ExperienceLevel
import com.marky.strivefit.data.values.FocusArea
import java.util.Date
@Entity(
    tableName = "workout_plans",
    indices = [
        Index(value = ["user_id", "name"], unique = true),
        Index(value = ["user_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutPlanEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "is_system_generated", defaultValue = "0")
    val isSystemGenerated: Boolean = false,

    @ColumnInfo(name = "difficulty_level")
    val difficultyLevel: ExperienceLevel?,

    @ColumnInfo(name = "focus_areas")
    val focusAreas: List<FocusArea>? = emptyList(),

    @ColumnInfo(name = "workout_style")
    val workoutStyle: List<String>? = emptyList(),

    @ColumnInfo(name = "intended_duration_minutes")
    val intendedDurationMinutes: Int?,

    @ColumnInfo(name = "required_equipment")
    val requiredEquipment: List<Equipments>? = emptyList(),

    @ColumnInfo(name = "is_favorite", defaultValue = "0")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "last_performed_at")
    val lastPerformedAt: Date?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?
)