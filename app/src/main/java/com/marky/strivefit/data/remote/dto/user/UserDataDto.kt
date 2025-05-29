package com.marky.strivefit.data.remote.dto.user

import com.marky.strivefit.data.values.Bmi
import com.marky.strivefit.data.values.ExerciseGoals
import com.marky.strivefit.data.values.ExperienceLevel
import com.marky.strivefit.data.values.FocusArea
import com.marky.strivefit.data.values.Sex

data class UserDataDto(
    var uuid: String,
    var username: String,
    var sex: Sex?,
    var focusArea: List<FocusArea>,
    var bmi: Bmi,
    var exerciseGoals: List<ExerciseGoals>,
    var experienceLevel: ExperienceLevel,
    var xp: Int = 0,
    var level: Int = 1,
    var weight: Double,
    var height: Double,
    var setupFinished: Double
)