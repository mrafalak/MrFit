package com.mrapps.data.di

import com.mrapps.data.manager.exercise_get_list.EnduranceExerciseListGetter
import com.mrapps.data.manager.exercise_get_list.ExerciseListGetter
import com.mrapps.data.manager.exercise_get_list.StrengthExerciseListGetter
import com.mrapps.data.util.ExerciseTypeKey
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoMap

@Module
@InstallIn(SingletonComponent::class)
abstract class ExerciseListGetterModule {

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.STRENGTH)
    abstract fun bindStrengthListGetter(getter: StrengthExerciseListGetter): ExerciseListGetter

    @Binds
    @IntoMap
    @ExerciseTypeKey(ExerciseTypeEnum.ENDURANCE)
    abstract fun bindEnduranceListGetter(getter: EnduranceExerciseListGetter): ExerciseListGetter
}