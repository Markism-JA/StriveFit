package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.app.ExerciseEntity
import com.marky.strivefit.data.local.entities.user.UserDataEntity
import com.marky.strivefit.data.values.GoalType
import com.marky.strivefit.data.values.RepeatInterval
import java.util.Date
@Entity(
    tableName = "user_goals",
    foreignKeys = [
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["exercise_id"])
    ]
)
data class UserGoalEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "title")
    var title: String = "",

    @ColumnInfo(name = "goal_type")
    val goalType: GoalType,

    @ColumnInfo(name = "target_value")
    val targetValue: Double = 0.0,

    @ColumnInfo(name = "current_value")
    var currentValue: Double = 0.0,

    @ColumnInfo(name = "unit")
    val unit: String?,

    @ColumnInfo(name = "exercise_id")
    val exerciseId: String?,

    @ColumnInfo(name = "start_date")
    var startDate: Date?,

    @ColumnInfo(name = "target_end_date")
    var targetEndDate: Date?,

    @ColumnInfo(name = "is_completed", defaultValue = "0")
    var isCompleted: Boolean = false,

    @ColumnInfo(name = "completed_date")
    var completedDate: Date?,

    @ColumnInfo(name = "target_frequency")
    val targetFrequency: Int?,

    @ColumnInfo(name = "repeat_interval")
    val repeatInterval: RepeatInterval?,

    @ColumnInfo(name = "repeat_days")
    val repeatDays: List<String>? = emptyList(),

    @ColumnInfo(name = "notes")
    val notes: String?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?
)