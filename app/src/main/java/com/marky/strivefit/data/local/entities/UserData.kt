package com.marky.strivefit.data.local.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.marky.strivefit.data.values.Bmi
import com.marky.strivefit.data.values.ExerciseGoals
import com.marky.strivefit.data.values.ExperienceLevel
import com.marky.strivefit.data.values.FocusArea
import com.marky.strivefit.data.values.Sex

@Entity(
    tableName = "UserData",
    indices = [
        Index(value = ["uuid"], unique = true),
        Index(value = ["username"], unique = true),
    ]
)
data class UserDataEntity(
    @PrimaryKey
    @ColumnInfo(name = "uuid") val uuid: String?,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "sex") val sex: Sex?,
    val focusArea: List<FocusArea>,
    val bmi: Bmi,
    val exerciseGoals: List<ExerciseGoals>,
    @ColumnInfo(name = "experience_level", defaultValue = "BEGINNER") val experienceLevel: ExperienceLevel,
    @ColumnInfo(name = "xp", defaultValue = "0") val xp: Int = 0,
    @ColumnInfo(name = "level", defaultValue = "1") val level: Int = 1,
    val weight: Double, //metric
    val height: Double //metric convert using app preferences and app logic
)


