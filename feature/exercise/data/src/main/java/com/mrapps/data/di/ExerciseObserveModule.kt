package com.mrapps.data.di

import com.mrapps.data.manager.exercise_observe.EnduranceExerciseObserver
import com.mrapps.data.manager.exercise_observe.ExerciseObserver
import com.mrapps.data.manager.exercise_observe.StrengthExerciseObserver
import com.mrapps.data.util.ExerciseTypeKey
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class ExerciseObserveModule {

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.STRENGTH)
    abstract fun bindStrengthObserver(observer: StrengthExerciseObserver): ExerciseObserver

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.ENDURANCE)
    abstract fun bindEnduranceObserver(observer: EnduranceExerciseObserver): ExerciseObserver
}