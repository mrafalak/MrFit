package com.mrapps.data.manager.exercise_add

import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.Result
import javax.inject.Inject

class ExerciseAddManager @Inject constructor(
    private val adders: Map<@JvmSuppressWildcards ExerciseTypeEnum, @JvmSuppressWildcards ExerciseAdder>
) {
    suspend fun addExercise(exercise: Exercise): Result<Unit, DataError.Local> {
        val adder = adders[exercise.type.type] ?: return Result.Error(DataError.Local.UNKNOWN)

        return adder.add(exercise)
    }
}