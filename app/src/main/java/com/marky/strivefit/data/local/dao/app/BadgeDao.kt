package com.marky.strivefit.data.local.dao.app

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.marky.strivefit.data.local.entities.app.BadgeEntity

@Dao
interface BadgeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadges(badges: List<BadgeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBadge(badge: BadgeEntity)

    @Query("SELECT * FROM Badges")
    suspend fun getAllBadges(): List<BadgeEntity>

    @Query("SELECT * FROM Badges WHERE id = :badgeId")
    suspend fun getBadgeById(badgeId: String): BadgeEntity?

    @Query("DELETE FROM Badges")
    suspend fun clearAllBadges()
}