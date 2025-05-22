import androidx.room.*
import androidx.room.RoomDatabase
import java.util.Date

enum class UserSex {
    MALE, FEMALE
}

enum class ExperienceLevel {
    BEGINNER, INTERMEDIATE, ADVANCED
}

enum class EquipmentCategory {
    BODYWEIGHT, DUMBBELLS, BARBELL, KETTLEBELLS, RESISTANCE_BANDS, MACHINE, CARDIO_EQUIPMENT, OTHER
}

enum class ExerciseType {
    STRENGTH, CARDIO, MOBILITY, PLYOMETRICS, STRETCHING, BALANCE
}

enum class WorkoutStyle {
    CIRCUIT, SUPERSETS, AMRAP, EMOM, TRADITIONAL_SETS, HIIT, LISS
}

// --- Type Converters for Enums and Dates ---

class Converters {
    // UserSex
    @TypeConverter fun fromUserSex(value: UserSex?): String? = value?.name
    @TypeConverter fun toUserSex(value: String?): UserSex? = value?.let { UserSex.valueOf(it) }

    // ExperienceLevel
    @TypeConverter fun fromExperienceLevel(value: ExperienceLevel): String = value.name
    @TypeConverter fun toExperienceLevel(value: String): ExperienceLevel = ExperienceLevel.valueOf(value)

    // EquipmentCategory
    @TypeConverter fun fromEquipmentCategory(value: EquipmentCategory): String = value.name
    @TypeConverter fun toEquipmentCategory(value: String): EquipmentCategory = EquipmentCategory.valueOf(value)

    // ExerciseType
    @TypeConverter fun fromExerciseType(value: ExerciseType): String = value.name
    @TypeConverter fun toExerciseType(value: String): ExerciseType = ExerciseType.valueOf(value)

    // WorkoutStyle
    @TypeConverter fun fromWorkoutStyle(value: WorkoutStyle?): String? = value?.name
    @TypeConverter fun toWorkoutStyle(value: String?): WorkoutStyle? = value?.let { WorkoutStyle.valueOf(it) }

    // Date/Timestamp (store as Long - epoch milliseconds)
    @TypeConverter fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }
    @TypeConverter fun dateToTimestamp(date: Date?): Long? = date?.time

    // Note: For created_at/updated_at with defaultValue = "CURRENT_TIMESTAMP",
    // Room handles this if the column type is INTEGER (for Long) or TEXT.
    // If you manage these in Kotlin, initialize with System.currentTimeMillis().
}


// --- User Profile & Settings ---

@Entity(
    tableName = "users",
    indices = [
        Index(value = ["firebase_uuid"], unique = true),
        Index(value = ["username"], unique = true),
        Index(value = ["email"], unique = true)
    ]
)
data class User(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,

    @ColumnInfo(name = "firebase_uuid") val firebaseUuid: String?,
    @ColumnInfo(name = "username") val username: String,
    @ColumnInfo(name = "sex") val sex: UserSex?,
    @ColumnInfo(name = "email") val email: String?,

    @ColumnInfo(name = "experience_level", defaultValue = "BEGINNER")
    val experienceLevel: ExperienceLevel = ExperienceLevel.BEGINNER,

    @ColumnInfo(name = "xp", defaultValue = "0") val xp: Int = 0,
    @ColumnInfo(name = "level", defaultValue = "1") val level: Int = 1,

    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP")
    val createdAt: Long = System.currentTimeMillis(), // Or handle via DB default

    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP")
    val updatedAt: Long = System.currentTimeMillis() // Or handle via DB default
)

@Entity(
    tableName = "equipment_master",
    indices = [Index(value = ["name"], unique = true)]
)
data class EquipmentMaster(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: EquipmentCategory,
    @ColumnInfo(name = "description") val description: String?
)

@Entity(
    tableName = "user_available_equipment",
    primaryKeys = ["user_id", "equipment_master_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EquipmentMaster::class,
            parentColumns = ["id"],
            childColumns = ["equipment_master_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserAvailableEquipment(
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "equipment_master_id", index = true) val equipmentMasterId: Int
    // quantity and notes from DBML can be added here if needed
)

@Entity(
    tableName = "goal_definitions",
    indices = [Index(value = ["name"], unique = true)]
)
data class GoalDefinition(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?
)

@Entity(
    tableName = "user_goals",
    primaryKeys = ["user_id", "goal_definition_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = GoalDefinition::class,
            parentColumns = ["id"],
            childColumns = ["goal_definition_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserGoal(
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "goal_definition_id", index = true) val goalDefinitionId: Int,
    @ColumnInfo(name = "is_active", defaultValue = "1") val isActive: Boolean = true, // SQLite stores Boolean as 0 or 1
    @ColumnInfo(name = "date_set", defaultValue = "CURRENT_TIMESTAMP") val dateSet: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "focus_area_definitions",
    indices = [Index(value = ["name"], unique = true)]
)
data class FocusAreaDefinition(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String, // e.g., Chest, Legs, Core Strength, Endurance
    @ColumnInfo(name = "description") val description: String?
)

@Entity(
    tableName = "user_focus_areas",
    primaryKeys = ["user_id", "focus_area_definition_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = FocusAreaDefinition::class,
            parentColumns = ["id"],
            childColumns = ["focus_area_definition_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserFocusArea(
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "focus_area_definition_id", index = true) val focusAreaDefinitionId: Int
)

@Entity(
    tableName = "workout_preferences",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class WorkoutPreference(
    @PrimaryKey
    @ColumnInfo(name = "user_id") val userId: Int, // User can only have one set of preferences
    @ColumnInfo(name = "days_per_week") val daysPerWeek: Int, // Add validation in app logic (1-7)
    @ColumnInfo(name = "session_duration_minutes") val sessionDurationMinutes: Int,
    @ColumnInfo(name = "preferred_workout_style") val preferredWorkoutStyle: WorkoutStyle?
)


// --- Activity Tracking ---

@Entity(
    tableName = "step_records",
    primaryKeys = ["user_id", "record_date"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class StepRecord(
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "record_date") val recordDate: Long, // Store date as epoch day or start of day ms
    @ColumnInfo(name = "steps", defaultValue = "0") val steps: Int = 0,
    @ColumnInfo(name = "distance_km", defaultValue = "0.0") val distanceKm: Float = 0.0f,
    @ColumnInfo(name = "calories_burned", defaultValue = "0") val caloriesBurned: Int = 0,
    @ColumnInfo(name = "last_updated", defaultValue = "CURRENT_TIMESTAMP") val lastUpdated: Long = System.currentTimeMillis()
)


// --- Exercise & Workout Structure ---

@Entity(
    tableName = "exercises",
    indices = [
        Index(value = ["api_id"], unique = true),
        Index(value = ["name"], unique = true)
    ],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["created_by_user_id"],
            onDelete = ForeignKey.SET_NULL // If user is deleted, custom exercise becomes system or orphan
        )
    ]
)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "api_id") val apiId: Int?, // ID from an external exercise API, if used
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "primary_body_part") val primaryBodyPart: String,
    @ColumnInfo(name = "target_muscle") val targetMuscle: String,
    @ColumnInfo(name = "secondary_muscles") val secondaryMuscles: String?, // Comma-separated or JSON
    @ColumnInfo(name = "equipment_required") val equipmentRequired: EquipmentCategory,
    @ColumnInfo(name = "instructions") val instructions: String,
    @ColumnInfo(name = "gif_url") val gifUrl: String?,
    @ColumnInfo(name = "video_url") val videoUrl: String?,
    @ColumnInfo(name = "exercise_type") val exerciseType: ExerciseType,
    @ColumnInfo(name = "difficulty") val difficulty: Int, // App-level validation 1-5
    @ColumnInfo(name = "is_public", defaultValue = "1") val isPublic: Boolean = true,
    @ColumnInfo(name = "created_by_user_id", index = true) val createdByUserId: Int?
)

@Entity(
    tableName = "workout_plans",
    indices = [Index(value = ["user_id", "name"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
        // ForeignKey for focus_area_definition_id if uncommented in DBML
    ]
)
data class WorkoutPlan(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_id", index = true) val userId: Int, // Creator of the plan
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "is_public", defaultValue = "0") val isPublic: Boolean = false,
    // @ColumnInfo(name = "focus_area_definition_id", index = true) val focusAreaDefinitionId: Int?,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") val createdAt: Long = System.currentTimeMillis(),
    @ColumnInfo(name = "updated_at", defaultValue = "CURRENT_TIMESTAMP") val updatedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "workout_plan_exercises",
    indices = [Index(value = ["workout_plan_id", "exercise_order"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutPlan::class,
            parentColumns = ["id"],
            childColumns = ["workout_plan_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE // Or RESTRICT if an exercise shouldn't be deletable if in use
        )
    ]
)
data class WorkoutPlanExercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "workout_plan_id", index = true) val workoutPlanId: Int,
    @ColumnInfo(name = "exercise_id", index = true) val exerciseId: Int,
    @ColumnInfo(name = "exercise_order") val exerciseOrder: Int,
    @ColumnInfo(name = "sets") val sets: Int?,
    @ColumnInfo(name = "reps_min") val repsMin: Int?,
    @ColumnInfo(name = "reps_max") val repsMax: Int?,
    @ColumnInfo(name = "duration_seconds") val durationSeconds: Int?,
    @ColumnInfo(name = "rest_period_seconds") val restPeriodSeconds: Int?,
    @ColumnInfo(name = "notes") val notes: String?
)


// --- Workout Logging ---

@Entity(
    tableName = "workout_sessions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = WorkoutPlan::class,
            parentColumns = ["id"],
            childColumns = ["workout_plan_id"],
            onDelete = ForeignKey.SET_NULL // If plan is deleted, session remains but unlinked
        )
    ]
)
data class WorkoutSession(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "workout_plan_id", index = true) val workoutPlanId: Int?,
    @ColumnInfo(name = "session_name") val sessionName: String?,
    @ColumnInfo(name = "start_time") val startTime: Long, // Not nullable
    @ColumnInfo(name = "end_time") val endTime: Long?,
    @ColumnInfo(name = "status", defaultValue = "started") val status: String = "started", // Consider enum
    @ColumnInfo(name = "mood") val mood: Int?, // App-level validation 1-5
    @ColumnInfo(name = "perceived_exertion") val perceivedExertion: Int?, // App-level validation 1-10
    @ColumnInfo(name = "total_calories_burned", defaultValue = "0") val totalCaloriesBurned: Int = 0,
    @ColumnInfo(name = "notes") val notes: String?
)

@Entity(
    tableName = "logged_exercises",
    // Optional unique index if (session, exercise, set_number) must be unique,
    // though PK 'id' usually suffices. Add it if you might insert without an auto-gen id.
    // indices = [Index(value = ["workout_session_id", "exercise_id", "set_number"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = WorkoutSession::class,
            parentColumns = ["id"],
            childColumns = ["workout_session_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.RESTRICT // Don't delete exercise if it's logged
        )
        // ForeignKey for workout_plan_exercise_id if uncommented in DBML
    ]
)
data class LoggedExercise(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "workout_session_id", index = true) val workoutSessionId: Int,
    @ColumnInfo(name = "exercise_id", index = true) val exerciseId: Int,
    // @ColumnInfo(name = "workout_plan_exercise_id", index = true) val workoutPlanExerciseId: Int?,
    @ColumnInfo(name = "set_number") val setNumber: Int,
    @ColumnInfo(name = "reps_completed") val repsCompleted: Int?,
    @ColumnInfo(name = "weight_kg") val weightKg: Float?,
    @ColumnInfo(name = "duration_seconds") val durationSeconds: Int?,
    @ColumnInfo(name = "distance_km") val distanceKm: Float?,
    @ColumnInfo(name = "calories_burned_per_set") val caloriesBurnedPerSet: Int?,
    @ColumnInfo(name = "rest_taken_seconds") val restTakenSeconds: Int?,
    @ColumnInfo(name = "notes") val notes: String?
)


// --- Progression & Gamification ---

@Entity(
    tableName = "exercise_personal_records",
    indices = [Index(value = ["user_id", "exercise_id", "record_date"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Exercise::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        )
        // ForeignKey for workout_session_id if uncommented in DBML
    ]
)
data class ExercisePersonalRecord(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "exercise_id", index = true) val exerciseId: Int,
    @ColumnInfo(name = "record_date", defaultValue = "CURRENT_TIMESTAMP") val recordDate: Long = System.currentTimeMillis(), // Store as epoch day or exact ms
    @ColumnInfo(name = "max_weight_kg") val maxWeightKg: Float?,
    @ColumnInfo(name = "max_reps_at_weight") val maxRepsAtWeight: Float?,
    @ColumnInfo(name = "max_reps") val maxReps: Int?,
    @ColumnInfo(name = "max_duration_seconds") val maxDurationSeconds: Int?,
    @ColumnInfo(name = "max_distance_km") val maxDistanceKm: Float?
    // @ColumnInfo(name = "workout_session_id", index = true) val workoutSessionId: Int?
)

@Entity(
    tableName = "xp_records",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class XpRecord(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "amount") val amount: Int,
    @ColumnInfo(name = "source_description") val sourceDescription: String,
    @ColumnInfo(name = "source_entity_id") val sourceEntityId: Int?,
    @ColumnInfo(name = "source_entity_type") val sourceEntityType: String?,
    @ColumnInfo(name = "earned_at", defaultValue = "CURRENT_TIMESTAMP") val earnedAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "badges", // Renamed from DBML "badges"
    indices = [Index(value = ["name"], unique = true)]
)
data class Badge( // Defined before achievement_definitions due to FK
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "image_url") val imageUrl: String?,
    @ColumnInfo(name = "created_at", defaultValue = "CURRENT_TIMESTAMP") val createdAt: Long = System.currentTimeMillis()
)

@Entity(
    tableName = "achievement_definitions",
    indices = [Index(value = ["name"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = Badge::class,
            parentColumns = ["id"],
            childColumns = ["awards_badge_id"],
            onDelete = ForeignKey.SET_NULL // If badge is deleted, achievement remains but awards no badge
        )
    ]
)
data class AchievementDefinition(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "criteria_description") val criteriaDescription: String,
    // criteria_logic jsonb: Store as String, parse with Gson/Moshi if needed
    // @ColumnInfo(name = "criteria_logic") val criteriaLogic: String?,
    @ColumnInfo(name = "xp_reward", defaultValue = "0") val xpReward: Int = 0,
    @ColumnInfo(name = "awards_badge_id", index = true) val awardsBadgeId: Int?,
    @ColumnInfo(name = "is_active", defaultValue = "1") val isActive: Boolean = true
)

@Entity(
    tableName = "user_achievements",
    indices = [Index(value = ["user_id", "achievement_definition_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = AchievementDefinition::class,
            parentColumns = ["id"],
            childColumns = ["achievement_definition_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class UserAchievement(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    @ColumnInfo(name = "user_id", index = true) val userId: Int,
    @ColumnInfo(name = "achievement_definition_id", index = true) val achievementDefinitionId: Int,
    @ColumnInfo(name = "earned_at", defaultValue = "CURRENT_TIMESTAMP") val earnedAt: Long = System.currentTimeMillis()
)


// --- Database Class ---
@Database(
    entities = [
        User::class, EquipmentMaster::class, UserAvailableEquipment::class,
        GoalDefinition::class, UserGoal::class, FocusAreaDefinition::class, UserFocusArea::class,
        WorkoutPreference::class, StepRecord::class, Exercise::class, WorkoutPlan::class,
        WorkoutPlanExercise::class, WorkoutSession::class, LoggedExercise::class,
        ExercisePersonalRecord::class, XpRecord::class, AchievementDefinition::class,
        UserAchievement::class, Badge::class
    ],
    version = 1, // Increment version on schema changes
    exportSchema = true // Recommended for migrations
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    // --- DAOs would be defined here ---
    // abstract fun userDao(): UserDao
    // abstract fun exerciseDao(): ExerciseDao
    // ... and so on for each major entity or group of related entities
}