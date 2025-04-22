package com.marky.strivefit.data.local.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "User",
    indices = [Index(value = ["email"], unique = true)]
)
data class User (
    @PrimaryKey(autoGenerate = true) val id: Int,
    val username: String,
    val uuid: String?,
    val sex: String,
    val email: String?,
    val createdAt: String,
    val height: Double,
    val weight: Int,
    val age: Int,
    val fullname: String
)


