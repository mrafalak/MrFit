package com.mrapps.data.manager.exercise_get

import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.Result
import javax.inject.Inject

class ExerciseGetManager @Inject constructor(
    private val getters: Map<@JvmSuppressWildcards ExerciseTypeEnum, @JvmSuppressWildcards ExerciseGetter>
) {
    suspend fun getExercise(exerciseEntity: ExerciseEntity): Result<Exercise, DataError.Local> {
        val getter = getters[exerciseEntity.type] ?: return Result.Error(DataError.Local.UNKNOWN)

        return getter.get(exerciseEntity)
    }
}