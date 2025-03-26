package com.mrapps.data.manager.exercise_get_list

import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.Result
import javax.inject.Inject

class ExerciseGetListManager @Inject constructor(
    private val getters: Map<@JvmSuppressWildcards ExerciseTypeEnum, @JvmSuppressWildcards ExerciseListGetter>
) {
    suspend fun getExerciseList(typeEnum: ExerciseTypeEnum): Result<List<Exercise>, DataError.Local> {
        val getter = getters[typeEnum] ?: return Result.Error(DataError.Local.UNKNOWN)

        return getter.getList()
    }
}