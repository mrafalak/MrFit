package com.mrapps.data.manager.exercise_observe

import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

class ExerciseObserveManager @Inject constructor(
    private val observers: Map<@JvmSuppressWildcards ExerciseTypeEnum, @JvmSuppressWildcards ExerciseObserver>
) {
    fun observeExercises(type: ExerciseTypeEnum): Flow<Result<List<Exercise>, DataError.Local>> {
        val observer = observers[type] ?: return flowOf(Result.Error(DataError.Local.UNKNOWN))
        return observer.observe()
    }
}