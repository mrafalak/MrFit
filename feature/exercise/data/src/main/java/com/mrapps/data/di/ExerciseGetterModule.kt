package com.mrapps.data.di

import com.mrapps.data.manager.exercise_get.EnduranceExerciseGetter
import com.mrapps.data.manager.exercise_get.ExerciseGetter
import com.mrapps.data.manager.exercise_get.StrengthExerciseGetter
import com.mrapps.data.util.ExerciseTypeKey
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap


@Module
@InstallIn(SingletonComponent::class)
abstract class ExerciseGetterModule {

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.STRENGTH)
    abstract fun bindStrengthGetter(getter: StrengthExerciseGetter): ExerciseGetter

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.ENDURANCE)
    abstract fun bindEnduranceGetter(getter: EnduranceExerciseGetter): ExerciseGetter
}