package com.mrapps.data.di

import com.mrapps.data.repository.ExerciseRepositoryImpl
import com.mrapps.domain.repository.ExerciseRepository
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
    abstract fun bindRepository(
        exerciseRepository: ExerciseRepositoryImpl
    ): ExerciseRepository
}