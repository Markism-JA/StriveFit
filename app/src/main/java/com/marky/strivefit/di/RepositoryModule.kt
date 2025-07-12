package com.marky.strivefit.di

import com.marky.strivefit.data.local.repository.AuthRepository
import com.marky.strivefit.data.local.repository.UserRepository
import com.marky.strivefit.data.remote.repository.AuthRepositoryImpl
import com.marky.strivefit.data.remote.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ): UserRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository
}