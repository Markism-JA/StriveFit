package com.marky.strivefit.data.local.dao.app // Or your preferred DAO package

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteQuery
import com.marky.strivefit.data.local.entities.app.ExerciseEntity
import com.marky.strivefit.data.values.Equipments
import com.marky.strivefit.data.values.ExerciseType
import com.marky.strivefit.data.values.Targets

@Dao
interface ExerciseDao {
    @Query("SELECT id, name, equipment, exercise_type, target_muscle, gif_url FROM Exercises")
    suspend fun getExerciseSummaries(): List<ExerciseSummary>

    // Filtered by keyword or difficulty
    @RawQuery
    suspend fun searchRaw(query: SupportSQLiteQuery): List<ExerciseSummary>

    // Full details
    @Query("SELECT * FROM Exercises WHERE id = :exerciseId")
    suspend fun getExerciseById(exerciseId: String): ExerciseEntity?
}

data class ExerciseSummary(
    val id: String,
    val name: String,
    val equipment: String,

    @ColumnInfo(name = "exercise_type")
    val exerciseType: String,

    @ColumnInfo(name = "target_muscle")
    val targetMuscle: String,

    @ColumnInfo(name = "gif_url")
    val gifUrl: String
)

fun buildSearchQuery(filters: SearchFilters): SupportSQLiteQuery {
    val sql = StringBuilder("SELECT id, name, equipment, exercise_type, gif_url FROM Exercises WHERE 1=1")
    val args = mutableListOf<Any>()

    // Fuzzy keyword match (only on 'name')
    for (word in filters.keywords) {
        sql.append(" AND name LIKE ?")
        args += "%$word%"
    }

    // Enum filters (exact match)
    filters.equipment?.let {
        sql.append(" AND equipment = ?")
        args += it.name
    }

    filters.exerciseType?.let {
        sql.append(" AND exercise_type = ?")
        args += it.name
    }

    filters.targetMuscle?.let {
        sql.append(" AND target_muscle = ?")
        args += it.name
    }

    return SimpleSQLiteQuery(sql.toString(), args.toTypedArray())
}

data class SearchFilters(
    val keywords: List<String> = emptyList(),
    val equipment: Equipments? = null,
    val exerciseType: ExerciseType? = null,
    val targetMuscle: Targets? = null
)

fun parseSearchInput(input: String): SearchFilters {
    val keywords = mutableListOf<String>()
    var equipment: Equipments? = null
    var exerciseType: ExerciseType? = null
    var targetMuscle: Targets? = null

    for (token in input.split(Regex("\\s+"))) {
        when {
            token.startsWith("equipment:", true) -> {
                val value = token.substringAfter(":").uppercase()
                equipment = Equipments.entries.find { it.name == value }
            }
            token.startsWith("type:", true) -> {
                val value = token.substringAfter(":").uppercase()
                exerciseType = ExerciseType.entries.find { it.name == value }
            }
            token.startsWith("target:", true) -> {
                val value = token.substringAfter(":").uppercase()
                targetMuscle = Targets.entries.find { it.name == value }
            }
            else -> keywords += token
        }
    }

    return SearchFilters(keywords, equipment, exerciseType, targetMuscle)
}