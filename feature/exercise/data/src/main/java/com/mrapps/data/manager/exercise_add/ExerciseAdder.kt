package com.mrapps.data.manager.exercise_add

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise

interface ExerciseAdder {
    suspend fun add(exercise: Exercise): Result<Unit, DataError.Local>
}