package com.mrapps.domain.handler

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum

interface ExerciseHandler {
    fun supports(type: ExerciseTypeEnum): Boolean
    suspend fun addExercise(exercise: Exercise): Result<Unit, DataError.Local>
}