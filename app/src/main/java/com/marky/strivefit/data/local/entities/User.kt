package com.marky.strivefit.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp

@Entity(tableName = "user")
class User (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val username: String,
    val uuid: String,
    val sex: String,
    val email: String,
    val created_at: Long
)
