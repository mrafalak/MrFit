package com.mrapps.data.manager.exercise_update

import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import javax.inject.Inject

class ExerciseUpdateManager @Inject constructor(
    private val updaters: Map<@JvmSuppressWildcards ExerciseTypeEnum, @JvmSuppressWildcards ExerciseUpdater>
) {
    suspend fun updateExercise(
        saved: ExerciseEntity,
        new: Exercise
    ): Result<Unit, DataError.Local> {
        val updater = updaters[new.type.type] ?: return Result.Error(DataError.Local.UNKNOWN)

        return updater.update(saved, new)
    }
}