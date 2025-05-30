package com.marky.strivefit.data.local.dao.user // Or your preferred DAO package

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Transaction
import com.marky.strivefit.data.local.entities.user.RunningSessionEntity
import com.marky.strivefit.data.values.RunningSummary
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface RunningSessionDao {

}
