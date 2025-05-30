package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.ActivityType
import com.marky.strivefit.data.values.PRMetric
import java.util.Date
@Entity(
    tableName = "personal_records",
    indices = [
        Index(value = ["user_id", "activity_type", "activity_name_or_id", "metric"], unique = true),
        Index(value = ["user_id"]),
        Index(value = ["source_session_id"]),
        Index(value = ["source_logged_exercise_id"])
    ],
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = LoggedExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["source_logged_exercise_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class PersonalRecordEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "activity_type")
    val activityType: ActivityType,

    @ColumnInfo(name = "activity_name_or_id")
    val activityNameOrId: String,

    @ColumnInfo(name = "metric")
    val metric: PRMetric,

    @ColumnInfo(name = "value")
    val value: Double,

    @ColumnInfo(name = "unit")
    val unit: String,

    @ColumnInfo(name = "context_weight")
    val contextWeight: Double?,

    @ColumnInfo(name = "context_weight_unit")
    val contextWeightUnit: String?,

    @ColumnInfo(name = "context_distance")
    val contextDistance: Double?,

    @ColumnInfo(name = "context_distance_unit")
    val contextDistanceUnit: String?,

    @ColumnInfo(name = "context_duration_seconds")
    val contextDurationSeconds: Int?,

    @ColumnInfo(name = "achieved_at")
    val achievedAt: Date?,

    @ColumnInfo(name = "source_session_id")
    val sourceSessionId: String?,

    @ColumnInfo(name = "source_logged_exercise_id")
    val sourceLoggedExerciseId: String?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?
)