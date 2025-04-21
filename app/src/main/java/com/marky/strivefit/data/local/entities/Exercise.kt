package com.marky.strivefit.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Exercise"
)
data class Exercise (
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "api_id") val apiId: String,
    val name: String,
    @ColumnInfo(name = "body_part") val bodyPart: String,
    val equipment: String,
    val target: String,
    @ColumnInfo(name = "secondary_muscles") val secondarymuscles: String,
    val instructions: String,
    @ColumnInfo(name = "gif_url") val gifUrl: String,
    @ColumnInfo(name = "gif_path") val gifPath: String
)