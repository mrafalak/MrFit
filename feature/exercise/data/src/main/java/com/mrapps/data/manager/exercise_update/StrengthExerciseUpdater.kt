package com.mrapps.data.manager.exercise_update

import com.mrapps.data.local.dao.exercise.type.strength.StrengthExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.local.util.safeMappingOperation
import com.mrapps.data.mapper.toExerciseWithStrengthEntities
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.Result
import javax.inject.Inject

class StrengthExerciseUpdater @Inject constructor(
    private val strengthExerciseDao: StrengthExerciseDao
) : ExerciseUpdater {

    override suspend fun update(
        saved: ExerciseEntity,
        new: Exercise
    ): Result<Unit, DataError.Local> {
        val mappingResult =
            safeMappingOperation<Pair<ExerciseEntity, StrengthExerciseEntity>, StrengthExerciseUpdater> {
                new.toExerciseWithStrengthEntities()
            }

        val (exerciseEntity, strengthEntity) = when (mappingResult) {
            is Result.Success -> mappingResult.data
            is Result.Error -> return Result.Error(mappingResult.error)
        }

        return if (saved.type == new.type.type) {
            safeDatabaseOperation<Unit, StrengthExerciseUpdater> {
                strengthExerciseDao.updateExerciseWithStrengthExercise(
                    exercise = exerciseEntity,
                    strengthExercise = strengthEntity
                )
            }
        } else {
            safeDatabaseOperation<Unit, StrengthExerciseUpdater> {
                strengthExerciseDao.removeSavedExerciseAndAddNewStrengthExercise(
                    exercise = exerciseEntity,
                    strengthExercise = strengthEntity
                )
            }
        }
    }
}