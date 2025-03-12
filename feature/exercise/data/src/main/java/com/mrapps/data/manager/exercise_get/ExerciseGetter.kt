package com.mrapps.data.manager.exercise_get

import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise

interface ExerciseGetter {
    suspend fun get(exerciseEntity: ExerciseEntity): Result<Exercise, DataError.Local>
}