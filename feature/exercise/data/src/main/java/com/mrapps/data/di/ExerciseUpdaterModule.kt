package com.mrapps.data.di

import com.mrapps.data.manager.exercise_update.EnduranceExerciseUpdater
import com.mrapps.data.manager.exercise_update.ExerciseUpdater
import com.mrapps.data.manager.exercise_update.StrengthExerciseUpdater
import com.mrapps.data.util.ExerciseTypeKey
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class ExerciseUpdaterModule {

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.STRENGTH)
    abstract fun bindStrengthUpdater(updater: StrengthExerciseUpdater): ExerciseUpdater

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.ENDURANCE)
    abstract fun bindEnduranceUpdater(updater: EnduranceExerciseUpdater): ExerciseUpdater
}