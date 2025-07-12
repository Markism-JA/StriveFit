package com.marky.strivefit.data.local.dao.app

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
import com.marky.strivefit.data.local.entities.app.ChallengeEntity
import com.marky.strivefit.data.values.ChallengeType
import com.marky.strivefit.data.values.RepeatInterval
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface ChallengeDao {
    @Query("SELECT * FROM Challenges")
    suspend fun getAllChallenges(): List<ChallengeEntity>

    @Query("SELECT * FROM Challenges WHERE id = :challengeId")
    suspend fun getChallengeById(challengeId: String): ChallengeEntity?

    @Query("SELECT id, name, xp_reward, challenge_type FROM Challenges")
    suspend fun getChallengeSummaries(): List<ChallengeSummary>
}

data class ChallengeSummary(
    val id: String,
    val name: String,

    @ColumnInfo(name = "xp_reward")
    val xpReward: Int,

    @ColumnInfo(name = "challenge_type")
    val challengeType: ChallengeType,
)