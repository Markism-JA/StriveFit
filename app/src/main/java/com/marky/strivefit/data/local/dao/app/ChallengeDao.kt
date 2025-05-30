package com.marky.strivefit.data.local.dao.app

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
}