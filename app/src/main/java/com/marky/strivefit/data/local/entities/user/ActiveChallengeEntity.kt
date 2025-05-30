package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.local.entities.app.ChallengeEntity
import java.util.Date
@Entity(
    tableName = "ActiveChallenges",
    foreignKeys = [
        ForeignKey(
            entity = ChallengeEntity::class,
            parentColumns = ["id"],
            childColumns = ["challenge_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["challenge_id"]),
    ]
)
data class ActiveChallenge(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "challenge_id")
    val challengeId: String,

    @ColumnInfo(name = "start_date")
    val startDate: Date?,

    @ColumnInfo(name = "current_progress")
    val currentProgress: Int = 0,

    @ColumnInfo(name = "last_progress_update")
    var lastProgressUpdate: Date?,

    @ColumnInfo(name = "streak_count")
    val streakCount: Int = 0,

    @ColumnInfo(name = "is_failed")
    val isFailed: Boolean = false,

    @ColumnInfo(name = "completed_date")
    var completedDate: Date?,

    @ColumnInfo(name = "created_at")
    var createdAt: Date?,

    @ColumnInfo(name = "updated_at")
    var updatedAt: Date?


)