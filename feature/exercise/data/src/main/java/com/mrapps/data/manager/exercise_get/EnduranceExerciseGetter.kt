package com.mrapps.data.manager.exercise_get

import com.mrapps.data.local.dao.exercise.type.endurance.EnduranceExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithType
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.mapper.toExercise
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.Result
import javax.inject.Inject

class EnduranceExerciseGetter @Inject constructor(
    private val enduranceExerciseDao: EnduranceExerciseDao
) : ExerciseGetter {

    override suspend fun get(exerciseEntity: ExerciseEntity): Result<Exercise, DataError.Local> {
        val getEnduranceExerciseResult =
            safeDatabaseOperation<EnduranceExerciseEntity, EnduranceExerciseGetter> {
                enduranceExerciseDao.getEnduranceExerciseById(exerciseEntity.id)
            }

        return when (getEnduranceExerciseResult) {
            is Result.Success -> {
                val exercise = ExerciseWithType.Endurance(
                    exercise = exerciseEntity,
                    enduranceExercise = getEnduranceExerciseResult.data
                ).toExercise()

                Result.Success(exercise)
            }

            is Result.Error -> {
                Result.Error(getEnduranceExerciseResult.error)
            }
        }
    }
}