package com.mrapps.domain.di

import com.mrapps.domain.handler.ExerciseHandler
import com.mrapps.domain.handler.StrengthExerciseHandler
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet

@Module
@InstallIn(SingletonComponent::class)
abstract class ExerciseHandlerModule {

    @Binds
    @IntoSet
    abstract fun bindStrengthExerciseHandler(handler: StrengthExerciseHandler): ExerciseHandler
}