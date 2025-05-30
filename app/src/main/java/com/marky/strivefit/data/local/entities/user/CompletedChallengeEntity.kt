package com.marky.strivefit.data.local.entities.user

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.ForeignKey
import com.marky.strivefit.data.local.entities.app.ChallengeEntity
import java.util.Date

@Entity(
    tableName = "CompletedChallenges",
    foreignKeys = [
        ForeignKey(
            entity = ChallengeEntity::class,
            parentColumns = ["id"],
            childColumns = ["challenge_id"],
            onDelete = ForeignKey.SET_NULL,
            onUpdate = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = UserDataEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE,
            onUpdate = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["challenge_id"]),
        Index(value = ["user_id", "challenge_id"])
    ]
)
data class CompletedChallengeEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,

    @ColumnInfo(name = "user_id")
    val userId: String,

    @ColumnInfo(name = "challenge_id")
    val challengeId: String,

    @ColumnInfo(name = "xp_earned")
    val xpEarned: Int,

    @ColumnInfo(name = "completion_date")
    var completionDate: Date?,

    @ColumnInfo(name = "start_date")
    var startDate: Date?,

    @ColumnInfo(name = "last_modified")
    var lastModified: Date?,

    @ColumnInfo(name = "last_synced")
    var lastSynced: Date?
)