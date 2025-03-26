package com.mrapps.data.manager.exercise_get

import com.mrapps.data.local.dao.exercise.type.strength.StrengthExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithType
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.mapper.toExercise
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.Result
import javax.inject.Inject

class StrengthExerciseGetter @Inject constructor(
    private val strengthExerciseDao: StrengthExerciseDao
) : ExerciseGetter {

    override suspend fun get(exerciseEntity: ExerciseEntity): Result<Exercise, DataError.Local> {
        val getStrengthExerciseResult =
            safeDatabaseOperation<StrengthExerciseEntity, StrengthExerciseGetter> {
                strengthExerciseDao.getStrengthExerciseById(exerciseEntity.id)
            }

        return when (getStrengthExerciseResult) {
            is Result.Success -> {
                val exercise = ExerciseWithType.Strength(
                    exercise = exerciseEntity,
                    strengthExercise = getStrengthExerciseResult.data
                ).toExercise()

                Result.Success(exercise)
            }

            is Result.Error -> {
                Result.Error(getStrengthExerciseResult.error)
            }
        }
    }
}