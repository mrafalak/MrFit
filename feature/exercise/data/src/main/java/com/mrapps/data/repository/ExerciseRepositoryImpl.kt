package com.mrapps.data.repository

import com.mrapps.data.manager.exercise_update.ExerciseUpdateManager
import com.mrapps.data.local.dao.exercise.ExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.manager.exercise_add.ExerciseAddManager
import com.mrapps.data.manager.exercise_get.ExerciseGetManager
import com.mrapps.data.manager.exercise_get_list.ExerciseGetListManager
import com.mrapps.data.manager.exercise_observe.ExerciseObserveManager
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.repository.ExerciseRepository
import com.mrapps.domain.Result
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val exerciseAddManager: ExerciseAddManager,
    private val exerciseGetManager: ExerciseGetManager,
    private val exerciseGetListManager: ExerciseGetListManager,
    private val exerciseUpdateManager: ExerciseUpdateManager,
    private val exerciseObserveManager: ExerciseObserveManager,
) : ExerciseRepository {

    override suspend fun getExerciseById(id: String): Result<Exercise, DataError.Local> {
        val getExerciseResult = safeDatabaseOperation<ExerciseEntity, ExerciseRepositoryImpl> {
            exerciseDao.getExerciseById(id)
        }

        return when (getExerciseResult) {
            is Result.Success -> {
                val exerciseEntity = getExerciseResult.data
                exerciseGetManager.getExercise(exerciseEntity)
            }

            is Result.Error -> Result.Error(getExerciseResult.error)
        }
    }

    override suspend fun getExerciseByName(name: String): Result<Exercise?, DataError.Local> {
        val getExerciseResult = safeDatabaseOperation<ExerciseEntity?, ExerciseRepositoryImpl> {
            exerciseDao.getExerciseByName(name)
        }

        return when (getExerciseResult) {
            is Result.Success -> {
                val exerciseEntity = getExerciseResult.data ?: return Result.Success(null)
                exerciseGetManager.getExercise(exerciseEntity)
            }

            is Result.Error -> Result.Error(getExerciseResult.error)
        }
    }

    override suspend fun addExercise(exercise: Exercise): Result<Unit, DataError.Local> {
        return exerciseAddManager.addExercise(exercise)
    }

    override suspend fun updateExercise(exercise: Exercise): Result<Unit, DataError.Local> {
        val getSavedExerciseResult = safeDatabaseOperation<ExerciseEntity, ExerciseRepositoryImpl> {
            exerciseDao.getExerciseById(exercise.id)
        }

        return when (getSavedExerciseResult) {
            is Result.Success -> {
                exerciseUpdateManager.updateExercise(
                    saved = getSavedExerciseResult.data,
                    new = exercise
                )
            }

            is Result.Error -> Result.Error(getSavedExerciseResult.error)
        }
    }

    override fun observeExercises(type: ExerciseTypeEnum): Flow<Result<List<Exercise>, DataError.Local>> {
        return exerciseObserveManager.observeExercises(type)
    }

    override suspend fun getExercisesByType(type: ExerciseTypeEnum): Result<List<Exercise>, DataError.Local> {
        return exerciseGetListManager.getExerciseList(type)
    }

    override suspend fun removeExerciseById(id: String): Result<Unit, DataError.Local> {
        return safeDatabaseOperation<Unit, ExerciseRepositoryImpl> {
            exerciseDao.deleteExerciseById(id)
        }
    }
}