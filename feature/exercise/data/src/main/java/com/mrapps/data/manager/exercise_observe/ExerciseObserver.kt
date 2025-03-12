package com.mrapps.data.manager.exercise_observe

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseObserver {
    fun observe(): Flow<Result<List<Exercise>, DataError.Local>>
}