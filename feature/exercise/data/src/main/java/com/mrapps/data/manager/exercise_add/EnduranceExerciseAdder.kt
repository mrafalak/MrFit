package com.mrapps.data.manager.exercise_add

import com.mrapps.data.local.dao.exercise.ExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.local.util.safeMappingOperation
import com.mrapps.data.mapper.toExerciseWithEnduranceEntities
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.Result
import javax.inject.Inject

class EnduranceExerciseAdder @Inject constructor(
    private val exerciseDao: ExerciseDao
) : ExerciseAdder {

    override suspend fun add(exercise: Exercise): Result<Unit, DataError.Local> {
        val mappingResult =
            safeMappingOperation<Pair<ExerciseEntity, EnduranceExerciseEntity>, EnduranceExerciseAdder> {
                exercise.toExerciseWithEnduranceEntities()
            }

        val (exerciseEntity, enduranceEntity) = when (mappingResult) {
            is Result.Success -> mappingResult.data
            is Result.Error -> return Result.Error(mappingResult.error)
        }

        return safeDatabaseOperation<Unit, EnduranceExerciseAdder> {
            exerciseDao.insertExerciseWithEnduranceExercise(
                exercise = exerciseEntity,
                enduranceExercise = enduranceEntity
            )
        }
    }
}