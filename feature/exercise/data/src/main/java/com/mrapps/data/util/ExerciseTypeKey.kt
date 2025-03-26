package com.mrapps.data.util

import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import dagger.MapKey

@MapKey
annotation class ExerciseTypeKey(val value: ExerciseTypeEnum)