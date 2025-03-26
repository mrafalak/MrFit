package com.mrapps.data.manager.exercise_update

import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise

interface ExerciseUpdater {
    suspend fun update(saved: ExerciseEntity, new: Exercise): Result<Unit, DataError.Local>
}