package com.marky.strivefit.data.local.dao.user // Or your preferred DAO package

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.marky.strivefit.data.local.entities.user.UserDataEntity
import com.marky.strivefit.data.values.ExperienceLevel
import com.marky.strivefit.data.values.FocusArea // Assuming this is an enum or type Room can handle
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface UserDataDao {
}
