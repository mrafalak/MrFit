package com.mrapps.data.manager.exercise_add

import com.mrapps.data.local.dao.exercise.ExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.local.util.safeMappingOperation
import com.mrapps.data.mapper.toExerciseWithStrengthEntities
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.Result
import javax.inject.Inject

class StrengthExerciseAdder @Inject constructor(
    private val exerciseDao: ExerciseDao
) : ExerciseAdder {

    override suspend fun add(exercise: Exercise): Result<Unit, DataError.Local> {
        val mappingResult =
            safeMappingOperation<Pair<ExerciseEntity, StrengthExerciseEntity>, StrengthExerciseAdder> {
                exercise.toExerciseWithStrengthEntities()
            }

        val (exerciseEntity, strengthEntity) = when (mappingResult) {
            is Result.Success -> mappingResult.data
            is Result.Error -> return Result.Error(mappingResult.error)
        }

        return safeDatabaseOperation<Unit, StrengthExerciseAdder> {
            exerciseDao.insertExerciseWithStrengthExercise(
                exercise = exerciseEntity,
                strengthExercise = strengthEntity
            )
        }
    }
}