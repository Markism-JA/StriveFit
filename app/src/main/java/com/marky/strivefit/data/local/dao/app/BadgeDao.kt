package com.marky.strivefit.data.local.dao.app

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marky.strivefit.data.local.entities.app.BadgeEntity

@Dao
interface BadgeDao {
    @Query("SELECT * FROM Badges")
    suspend fun getAllBadges(): List<BadgeEntity>

    @Query("SELECT id, icon_link FROM Badges")
    suspend fun getBadgeIcons(): List<BadgeIcon>

    @Query("SELECT * FROM Badges WHERE id = :badgeId")
    suspend fun getBadgeById(badgeId: String): BadgeEntity?
}

data class BadgeIcon(
    val id: String,

    @ColumnInfo(name = "icon_link")
    val iconLink: String
)