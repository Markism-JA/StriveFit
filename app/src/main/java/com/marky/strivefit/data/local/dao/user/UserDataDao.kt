package com.marky.strivefit.data.local.dao.user // Or your preferred DAO package

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.marky.strivefit.data.local.entities.user.UserDataEntity
import com.marky.strivefit.data.values.ExperienceLevel
import com.marky.strivefit.data.values.FocusArea // Assuming this is an enum or type Room can handle
import kotlinx.coroutines.flow.Flow
import java.util.Date

@Dao
interface UserDataDao {
    // Upsert = INSERT or UPDATE. It's perfect for syncing.
    @Upsert
    suspend fun upsertUser(user: UserDataEntity)

    @Query("SELECT * FROM userdata WHERE id = :userId")
    fun getUserById(userId: String): Flow<UserDataEntity?>

    @Query("SELECT * FROM userdata WHERE id = :userId")
    suspend fun getUserByIdOnce(userId: String): UserDataEntity?
}
