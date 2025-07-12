package com.marky.strivefit.di

import androidx.room.Room
import com.marky.strivefit.data.local.AppDatabase
import com.marky.strivefit.data.local.dao.user.UserDataDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import android.content.Context
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class) // This module's recipes are available app-wide
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "strivefit_database"
        ).build()
    }

    @Provides
    fun provideUserDao(appDatabase: AppDatabase): UserDataDao {
        // Hilt knows how to create AppDatabase from the recipe above,
        // so it can use it here to provide the UserDao.
        return appDatabase.userDataDao()
    }
}