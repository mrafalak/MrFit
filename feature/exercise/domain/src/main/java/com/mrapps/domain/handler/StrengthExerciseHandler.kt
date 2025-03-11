package com.mrapps.domain.handler

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.repository.ExerciseRepository
import javax.inject.Inject

class StrengthExerciseHandler @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) : ExerciseHandler {

    override fun supports(type: ExerciseTypeEnum): Boolean {
        return type == ExerciseTypeEnum.STRENGTH
    }

    override suspend fun addExercise(exercise: Exercise): Result<Unit, DataError.Local> {
        return exerciseRepository.addStrengthExercise(exercise)
    }
}