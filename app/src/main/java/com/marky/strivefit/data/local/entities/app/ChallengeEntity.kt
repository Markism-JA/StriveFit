package com.marky.strivefit.data.local.entities.app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.ChallengeType
import com.marky.strivefit.data.values.RepeatInterval
import java.util.Date

@Entity(
    tableName = "Challenges",
    indices = [
        Index(value = ["name"], unique = true),
        Index(value = ["id"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
           entity = BadgeEntity::class,
            parentColumns = ["id"],
            childColumns = ["badge_id"],
            onDelete = ForeignKey.Companion.CASCADE
        )
    ]
)
data class ChallengeEntity(
    @PrimaryKey
        @ColumnInfo(name = "id")
        val id: String,

    @ColumnInfo(name = "name")
        val name: String,

    @ColumnInfo(name = "title")
        val title: String,

    @ColumnInfo(name = "description")
        val description: String,

    @ColumnInfo(name = "challenge_type")
        val challengeType: ChallengeType,

    @ColumnInfo(name = "target_value")
        val targetValue: Int,

    @ColumnInfo(name = "repeat_interval")
        val repeatInterval: RepeatInterval,

    @ColumnInfo(name = "duration")
        val duration: Int,

    @ColumnInfo(name = "xp_reward")
        val xpReward: Int,

    @ColumnInfo(name = "badge_id", index = true)
        val badgeId: String?,

    @ColumnInfo(name = "created_at")
        var createdAt: Date?,

    @ColumnInfo(name = "updated_at")
        var updatedAt: Date?
)
