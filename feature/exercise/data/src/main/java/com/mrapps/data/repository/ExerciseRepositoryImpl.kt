package com.mrapps.data.repository

import com.mrapps.data.manager.exercise_update.ExerciseUpdateManager
import com.mrapps.data.local.dao.exercise.ExerciseDao
import com.mrapps.data.local.dao.exercise.type.endurance.EnduranceExerciseDao
import com.mrapps.data.local.dao.exercise.type.strength.StrengthExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithType
import com.mrapps.data.local.util.safeDatabaseFlowOperation
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.local.util.safeMappingOperation
import com.mrapps.data.manager.exercise_add.ExerciseAddManager
import com.mrapps.data.mapper.toExercise
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.repository.ExerciseRepository
import com.mrapps.domain.Result
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val strengthExerciseDao: StrengthExerciseDao,
    private val enduranceExerciseDao: EnduranceExerciseDao,
    private val exerciseAddManager: ExerciseAddManager,
    private val exerciseUpdateManager: ExerciseUpdateManager,
    private val dispatchers: DispatcherProvider
) : ExerciseRepository {

    override suspend fun getExerciseById(id: String): Result<Exercise, DataError.Local> {
        val getExerciseResult = safeDatabaseOperation<ExerciseEntity, ExerciseRepositoryImpl> {
            exerciseDao.getExerciseById(id)
        }

        return when (getExerciseResult) {
            is Result.Success -> {
                val exerciseEntity = getExerciseResult.data

                when (exerciseEntity.type) {
                    ExerciseTypeEnum.STRENGTH -> {
                        getStrengthExercise(exerciseEntity)
                    }

                    ExerciseTypeEnum.ENDURANCE -> {
                        getEnduranceExercise(exerciseEntity)
                    }
                }
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

                when (exerciseEntity.type) {
                    ExerciseTypeEnum.STRENGTH -> {
                        getStrengthExercise(exerciseEntity)
                    }

                    ExerciseTypeEnum.ENDURANCE -> {
                        getEnduranceExercise(exerciseEntity)
                    }
                }
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

    private suspend fun getStrengthExercise(exerciseEntity: ExerciseEntity): Result<Exercise, DataError.Local> {
        val getStrengthExerciseResult =
            safeDatabaseOperation<StrengthExerciseEntity, ExerciseRepositoryImpl> {
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

    private suspend fun getEnduranceExercise(exerciseEntity: ExerciseEntity): Result<Exercise, DataError.Local> {
        val getEnduranceExerciseResult =
            safeDatabaseOperation<EnduranceExerciseEntity, ExerciseRepositoryImpl> {
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

    override suspend fun getAllStrengthExercises(): Result<List<Exercise>, DataError.Local> {
        val getResult =
            safeDatabaseOperation<List<ExerciseWithType.Strength>, ExerciseRepositoryImpl> {
                exerciseDao.getAllStrengthExercises()
            }

        return when (getResult) {
            is Result.Error -> Result.Error(getResult.error)
            is Result.Success -> {
                val exerciseList: List<Exercise> = getResult.data.map {
                    val mapResult = safeMappingOperation<Exercise, ExerciseRepositoryImpl> {
                        it.toExercise()
                    }

                    when (mapResult) {
                        is Result.Success -> mapResult.data
                        is Result.Error -> return Result.Error(mapResult.error)
                    }
                }
                return Result.Success(exerciseList)
            }
        }
    }

    override fun observeStrengthExercises(): Flow<Result<List<Exercise>, DataError.Local>> {
        return safeDatabaseFlowOperation<List<Exercise>, ExerciseRepositoryImpl>(
            dispatcher = dispatchers.io
        ) {
            exerciseDao.observeStrengthExercises().map { entityList ->
                entityList.map { entity ->
                    entity.toExercise()
                }
            }
        }
    }

    override fun observeEnduranceExercises(): Flow<Result<List<Exercise>, DataError.Local>> {
        return safeDatabaseFlowOperation<List<Exercise>, ExerciseRepositoryImpl>(
            dispatcher = dispatchers.io
        ) {
            exerciseDao.observeEnduranceExercises().map { entityList ->
                entityList.map { entity ->
                    entity.toExercise()
                }
            }
        }
    }

    override suspend fun removeExerciseById(id: String): Result<Unit, DataError.Local> {
        return safeDatabaseOperation<Unit, ExerciseRepositoryImpl> {
            exerciseDao.deleteExerciseById(id)
        }
    }
}