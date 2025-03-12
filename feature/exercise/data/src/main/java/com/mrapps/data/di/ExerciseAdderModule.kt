package com.mrapps.data.di

import com.mrapps.data.manager.exercise_add.EnduranceExerciseAdder
import com.mrapps.data.manager.exercise_add.ExerciseAdder
import com.mrapps.data.manager.exercise_add.StrengthExerciseAdder
import com.mrapps.data.util.ExerciseTypeKey
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class ExerciseAdderModule {

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.STRENGTH)
    abstract fun bindStrengthAdder(adder: StrengthExerciseAdder): ExerciseAdder

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.ENDURANCE)
    abstract fun bindEnduranceAdder(adder: EnduranceExerciseAdder): ExerciseAdder
}