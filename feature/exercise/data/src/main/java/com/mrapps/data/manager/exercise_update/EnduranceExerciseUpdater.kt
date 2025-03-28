package com.mrapps.data.manager.exercise_update

import com.mrapps.data.local.dao.exercise.type.endurance.EnduranceExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.local.util.safeMappingOperation
import com.mrapps.data.mapper.toExerciseWithEnduranceEntities
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.Result

import javax.inject.Inject

class EnduranceExerciseUpdater @Inject constructor(
    private val enduranceExerciseDao: EnduranceExerciseDao
) : ExerciseUpdater {

    override suspend fun update(
        saved: ExerciseEntity,
        new: Exercise
    ): Result<Unit, DataError.Local> {
        val mappingResult =
            safeMappingOperation<Pair<ExerciseEntity, EnduranceExerciseEntity>, EnduranceExerciseUpdater> {
                new.toExerciseWithEnduranceEntities()
            }

        val (exerciseEntity, enduranceEntity) = when (mappingResult) {
            is Result.Success -> mappingResult.data
            is Result.Error -> return Result.Error(mappingResult.error)
        }

        return if (saved.type == new.type.type) {
            safeDatabaseOperation<Unit, EnduranceExerciseUpdater> {
                enduranceExerciseDao.updateExerciseWithEnduranceExercise(
                    exercise = exerciseEntity,
                    enduranceExercise = enduranceEntity
                )
            }
        } else {
            safeDatabaseOperation<Unit, EnduranceExerciseUpdater> {
                enduranceExerciseDao.removeSavedExerciseAndAddNewEnduranceExercise(
                    exercise = exerciseEntity,
                    enduranceExercise = enduranceEntity
                )
            }
        }
    }
}