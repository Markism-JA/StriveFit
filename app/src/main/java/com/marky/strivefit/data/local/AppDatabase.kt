package com.marky.strivefit.data.local // Or your main app package for database setup

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.marky.strivefit.data.local.dao.app.* // Assuming DAOs for 'app' entities are here
import com.marky.strivefit.data.local.dao.user.* // Assuming DAOs for 'user' entities are here
import com.marky.strivefit.data.local.entities.app.*
import com.marky.strivefit.data.local.entities.user.*
@Database(
        entities = [
BadgeEntity::class,
ChallengeEntity::class,
ExerciseEntity::class,

ActiveChallengeEntity::class,
CompletedChallengeEntity::class,
CyclingSessionEntity::class,
LoggedExerciseEntity::class,
PersonalRecordEntity::class,
RunningSessionEntity::class,
StepRecordsEntity::class,
UserDataEntity::class,
UserGoalEntity::class,
WorkoutPlanEntity::class,
WorkoutPlanExerciseEntity::class,
WorkoutSessionEntity::class
    ],
version = 1,
exportSchema = true
        )
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // DAOs for 'app' entities
    abstract fun badgeDao(): BadgeDao
    abstract fun challengeDao(): ChallengeDao
    abstract fun exerciseDao(): ExerciseDao

    // DAOs for 'user' entities
    abstract fun activeChallengeDao(): ActiveChallengeDao
    abstract fun completedChallengeDao(): CompletedChallengeDao
    abstract fun cyclingSessionDao(): CyclingSessionDao
    abstract fun loggedExerciseDao(): LoggedExerciseDao
    abstract fun personalRecordDao(): PersonalRecordDao
    abstract fun runningSessionDao(): RunningSessionDao
    abstract fun stepRecordDao(): StepRecordDao
    abstract fun userDataDao(): UserDataDao
    abstract fun userGoalDao(): UserGoalDao
    abstract fun workoutPlanDao(): WorkoutPlanDao
    abstract fun workoutPlanExerciseDao(): WorkoutPlanExerciseDao
    abstract fun workoutSessionDao(): WorkoutSessionDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private const val DATABASE_NAME = "strivefit_database"

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                )
                // .addMigrations(MIGRATION_1_2, MIGRATION_2_3) // Add your migrations here
                // .fallbackToDestructiveMigration() // Use only during development, remove for production!
                .build()
                INSTANCE = instance
                instance
            }
        }

    }
}