package com.mrapps.data.manager.exercise_get_list

import com.mrapps.data.local.dao.exercise.type.endurance.EnduranceExerciseDao
import com.mrapps.data.local.relation.ExerciseWithType
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.mapper.toExercise
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.Result
import javax.inject.Inject

class EnduranceExerciseListGetter @Inject constructor(
    private val enduranceExerciseDao: EnduranceExerciseDao
) : ExerciseListGetter {

    override suspend fun getList(): Result<List<Exercise>, DataError.Local> {
        val result =
            safeDatabaseOperation<List<ExerciseWithType.Endurance>, EnduranceExerciseListGetter> {
                enduranceExerciseDao.getAllEnduranceExercises()
            }

        return when (result) {
            is Result.Success -> {
                val list = result.data.map { it.toExercise() }
                Result.Success(list)
            }

            is Result.Error -> {
                Result.Error(result.error)
            }
        }
    }
}