package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.Bmi
import com.marky.strivefit.data.values.ExerciseGoals
import com.marky.strivefit.data.values.ExperienceLevel
import com.marky.strivefit.data.values.FocusArea
import com.marky.strivefit.data.values.Sex
import java.util.Date

@Entity(
    tableName = "UserData",
    indices = [
        Index(value = ["display_name"], unique = true) // If username/displayName should be unique
    ]
)
data class UserDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "email")
    var email: String?,

    @ColumnInfo(name = "display_name")
    var displayName: String?,

    @ColumnInfo(name = "photo_url")
    var photoUrl: String?,

    @ColumnInfo(name = "date_of_birth")
    var dateOfBirth: Date?,

    @ColumnInfo(name = "sex")
    var sex: Sex?,

    @ColumnInfo(name = "height_cm")
    var heightCm: Int?,

    @ColumnInfo(name = "weight_kg")
    var weightKg: Double?,

    @ColumnInfo(name = "experience_level")
    var experienceLevel: ExperienceLevel?,

    @ColumnInfo(name = "fcm_token")
    var fcmToken: String?,

    @ColumnInfo(name = "available_equipment")
    var availableEquipment: List<String> = emptyList(),

    @ColumnInfo(name = "focus_areas")
    var focusAreas: List<FocusArea> = emptyList(),

    @ColumnInfo(name = "xp", defaultValue = "0")
    var xp: Int = 0,

    @ColumnInfo(name = "level", defaultValue = "1")
    var level: Int = 1,

    @ColumnInfo(name = "setup_finished", defaultValue = "0")
    var setupFinished: Boolean = false,

    @ColumnInfo(name = "preferred_days_per_week")
    var preferredDaysPerWeek: Int?,

    @ColumnInfo(name = "preferred_session_duration_minutes")
    var preferredSessionDurationMinutes: Int?,

    @ColumnInfo(name = "workout_reminder_time")
    var workoutReminderTime: String?,

    @ColumnInfo(name = "default_rest_timer_seconds")
    var defaultRestTimerSeconds: Int?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?,

    @ColumnInfo(name = "last_login_at")
    var lastLoginAt: Date?

)