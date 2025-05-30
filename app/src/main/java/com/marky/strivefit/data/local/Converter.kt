package com.marky.strivefit.data.local

import androidx.room.TypeConverter
import com.marky.strivefit.data.values.ActivityType
import com.marky.strivefit.data.values.BodyParts
import com.marky.strivefit.data.values.ChallengeType
import com.marky.strivefit.data.values.Equipments
import com.marky.strivefit.data.values.ExerciseType
import com.marky.strivefit.data.values.ExperienceLevel
import com.marky.strivefit.data.values.FocusArea
import com.marky.strivefit.data.values.GoalType
import com.marky.strivefit.data.values.PRMetric
import com.marky.strivefit.data.values.RepeatInterval
import com.marky.strivefit.data.values.SecondaryMuscles
import com.marky.strivefit.data.values.Sex
import com.marky.strivefit.data.values.Targets
import com.marky.strivefit.data.values.WorkoutSessionStatus
import java.util.Date

class Converters {

    private val stringListDelimiter = ";;;"
    private val nullStringPlaceholder = "__NULL_STR__"


    // For List<String> (non-nullable list of non-nullable strings)
    @TypeConverter
    fun fromNonNullStringList(list: List<String>?): String? {
        return list?.joinToString(separator = stringListDelimiter)
    }

    @TypeConverter
    fun toNonNullStringList(data: String?): List<String> {
        return data?.takeIf { it.isNotEmpty() }?.split(stringListDelimiter)?.filter { it.isNotEmpty() } ?: emptyList()
    }

    private val nullableElementListDelimiter = ";;;"
    private val nullStringElementPlaceholder = "__NULL_STR_ELEMENT__"

    @TypeConverter
    fun fromNullableElementStrList(list: List<String?>?): String? {
        return list?.joinToString(separator = nullableElementListDelimiter) {
            it ?: nullStringElementPlaceholder
        }
    }

    @TypeConverter
    fun toNullableElementStrList(data: String?): List<String?> {
        return data?.split(nullableElementListDelimiter)?.map {
            if (it == nullStringElementPlaceholder) null else it
        } ?: emptyList()
    }

    @TypeConverter
    fun fromDate(date: Date?): Long? = date?.time

    @TypeConverter
    fun toDate(timestamp: Long?): Date? = timestamp?.let { Date(it) }

    // ChallengeType
    @TypeConverter fun fromChallengeType(value: ChallengeType?): String? = value?.name
    @TypeConverter fun toChallengeType(value: String?): ChallengeType? = value?.let { ChallengeType.valueOf(it) }

    // RepeatInterval
    @TypeConverter fun fromRepeatInterval(value: RepeatInterval?): String? = value?.name
    @TypeConverter fun toRepeatInterval(value: String?): RepeatInterval? = value?.let { RepeatInterval.valueOf(it) }

    // BodyParts
    @TypeConverter fun fromBodyParts(value: BodyParts?): String? = value?.name
    @TypeConverter fun toBodyParts(value: String?): BodyParts? = value?.let { BodyParts.valueOf(it) }

    // Equipments (Single Enum)
    @TypeConverter fun fromEquipment(value: Equipments?): String? = value?.name
    @TypeConverter fun toEquipment(value: String?): Equipments? = value?.let { Equipments.valueOf(it) }

    // Targets
    @TypeConverter fun fromTargets(value: Targets?): String? = value?.name
    @TypeConverter fun toTargets(value: String?): Targets? = value?.let { Targets.valueOf(it) }

    // ExerciseType
    @TypeConverter fun fromExerciseType(value: ExerciseType?): String? = value?.name
    @TypeConverter fun toExerciseType(value: String?): ExerciseType? = value?.let { ExerciseType.valueOf(it) }

    // PRMetric
    @TypeConverter fun fromPRMetric(value: PRMetric?): String? = value?.name
    @TypeConverter fun toPRMetric(value: String?): PRMetric? = value?.let { PRMetric.valueOf(it) }

    // Sex
    @TypeConverter fun fromSex(value: Sex?): String? = value?.name
    @TypeConverter fun toSex(value: String?): Sex? = value?.let { Sex.valueOf(it) }

    // ExperienceLevel
    @TypeConverter fun fromExperienceLevel(value: ExperienceLevel?): String? = value?.name
    @TypeConverter fun toExperienceLevel(value: String?): ExperienceLevel? = value?.let { ExperienceLevel.valueOf(it) }

    // WorkoutSessionStatus
    @TypeConverter fun fromWorkoutSessionStatus(value: WorkoutSessionStatus?): String? = value?.name
    @TypeConverter fun toWorkoutSessionStatus(value: String?): WorkoutSessionStatus? = value?.let { WorkoutSessionStatus.valueOf(it) }

    // NEW: ActivityType
    @TypeConverter fun fromActivityType(value: ActivityType?): String? = value?.name
    @TypeConverter fun toActivityType(value: String?): ActivityType? = value?.let { ActivityType.valueOf(it) }

    // NEW: GoalType
    @TypeConverter fun fromGoalType(value: GoalType?): String? = value?.name
    @TypeConverter fun toGoalType(value: String?): GoalType? = value?.let { GoalType.valueOf(it) }

    private val enumListDelimiter = ","

    // List<SecondaryMuscles>
    @TypeConverter
    fun fromSecondaryMusclesList(list: List<SecondaryMuscles>?): String? {
        return list?.takeIf { it.isNotEmpty() }?.map { it.name }?.joinToString(enumListDelimiter)
    }

    @TypeConverter
    fun toSecondaryMusclesList(data: String?): List<SecondaryMuscles> { // MODIFIED: Returns non-nullable
        return data?.takeIf { it.isNotEmpty() }?.split(enumListDelimiter)?.mapNotNull { runCatching { SecondaryMuscles.valueOf(it) }.getOrNull() }
            ?: emptyList()
    }

    // List<FocusArea>
    @TypeConverter
    fun fromFocusAreaList(list: List<FocusArea>?): String? {
        return list?.takeIf { it.isNotEmpty() }?.map { it.name }?.joinToString(enumListDelimiter)
    }

    @TypeConverter
    fun toFocusAreaList(data: String?): List<FocusArea> { // MODIFIED: Returns non-nullable
        return data?.takeIf { it.isNotEmpty() }?.split(enumListDelimiter)?.mapNotNull { runCatching { FocusArea.valueOf(it) }.getOrNull() }
            ?: emptyList()
    }

    // List<Equipments>
    @TypeConverter
    fun fromEquipmentsList(list: List<Equipments>?): String? {
        return list?.takeIf { it.isNotEmpty() }?.joinToString(enumListDelimiter) { it.name }
    }

    @TypeConverter
    fun toEquipmentsList(data: String?): List<Equipments>? {
        return data?.takeIf { it.isNotEmpty() }?.split(enumListDelimiter)?.mapNotNull { runCatching { Equipments.valueOf(it) }.getOrNull() }
            .let { if (data == null) null else it ?: emptyList() } // Handles null data -> null list, empty/invalid data string -> empty list
    }
}