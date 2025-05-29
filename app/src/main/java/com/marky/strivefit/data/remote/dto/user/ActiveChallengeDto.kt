package com.marky.strivefit.data.remote.dto.user

import java.util.Date
import androidx.annotation.Keep
import com.marky.strivefit.data.values.Bmi
import com.marky.strivefit.data.values.Equipments
import com.marky.strivefit.data.values.ExerciseGoals
import com.marky.strivefit.data.values.ExperienceLevel
import com.marky.strivefit.data.values.FocusArea
import com.marky.strivefit.data.values.GoalType
import com.marky.strivefit.data.values.PRMetric
import com.marky.strivefit.data.values.RepeatInterval
import com.marky.strivefit.data.values.Sex

@Keep
data class ActiveChallengeDto(
    var id: Int = 0,
    var userId: String,
    var challengeId: Int,
    var startDate: Long,
    var currentProgress: Int = 0,
    var streakCount: Int = 0,
    var isFailed: Boolean = false
)

@Keep
data class CompletedChallengeDto(
    var id: Int = 0,
    var challengeId: Int,
    var completionDate: Long,
    var xpEarned: Int
)

@Keep
data class CyclingSessionDto(
    var id: Int = 0,
    var userId: String,
    var startTime: Long,
    var durationSeconds: Int,
    var distanceMeters: Float,
    var caloriesBUrned: Double,
    var averageSpeedKmh: Float?,
    var maxSpeedKmh: Float?,
    var averageCadenceRpm: Double,
    var maxCadenceRpm: Int?,
    var averageHeartRateBpm: Double?,
    var maxHeartRateBpm: Int?,
    var gpsTrackJson: String?
)

@Keep
data class LoggedExerciseDto(
    var id: Int = 0,
    var workoutSessionId: Int,
    var workoutPlanExerciseId: Int?,
    var setNumber: Int,
    var repsCompleted: Int?,
    var weightKg: Float?,
    var durationSeconds: Int,
    var distanceKm: Float?,
    var caloriesBurnedPerSet: Int?,
    var actualRpe: Int?,
    var restTakenSeconds: Int?,
    var notes: String?,
    var LoggedAt: Date = Date()
)

@Keep
data class PersonalRecordDto(
    var id: Int = 0,
    var userId: String,
    var exerciseId: Int,
    var metric: PRMetric,
    var value: Float,
    var loggedExerciseId: Int?,
    var achievedAt: Date
)

@Keep
data class RunningSessionDto(
    var id: Int = 0,
    var userId: String,
    var startTime: Long,
    var durationSeconds: Int,
    var distanceMeters: Float,
    var caloriesBurned: Double,
    var averagePaceSecondsPerKm: Int?,
    var maxPaceSecondsPerKm: Int?,
    var averageHeartRateBpm: Int?,
    var maxHeartRateBpm: Int?,
    var steps: Int?,
    var gpsTrackJson: String?
)

@Keep
data class StepRecordsDto(
    var userId: String,
    var recordDate: Date,
    var steps: Int = 0,
    var distanceKm: Float = 0f,
    var caloriesBurned: Double = 0.0,
    var source: String? = null,
    var lastUpdated: Date = Date()
)

data class UserAvailableEquipmentDto(
    var userId: String,
    var equipment: Equipments
)

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

data class UserGoalDto(
    var id: Int = 0,
    var userId: Int?,
    var exerciseId: Int?,
    var goalType: GoalType,
    var targetValue: Int,
    var currentValue: Int = 0,
    var startDate: Date,
    var endDate: Date,
    var isCompleted: Boolean = false,

    var targetFrequency: Int = 1,
    var repeatInterval: RepeatInterval = RepeatInterval.NONE,
    var repeatDays: String? = null
)

data class WorkoutPlanDto(
    var id: Int = 0,
    var userId: String,
    var name: String,
    var description: String,
    var isPublic: Boolean,
    var difficultyLevel: ExperienceLevel?,
    val createdAt: Date = Date(),
    val updatedAt: Date = Date()

)