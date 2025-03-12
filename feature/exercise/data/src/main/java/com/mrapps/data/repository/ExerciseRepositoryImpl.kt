package com.mrapps.data.repository

import com.mrapps.data.local.dao.exercise.ExerciseDao
import com.mrapps.data.local.dao.exercise.type.strength.StrengthExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithStrengthExercise
import com.mrapps.data.local.util.safeDatabaseFlowOperation
import com.mrapps.data.local.util.safeDatabaseOperation
import com.mrapps.data.local.util.safeMappingOperation
import com.mrapps.data.mapper.toExercise
import com.mrapps.data.mapper.toExerciseWithStrengthEntities
import com.mrapps.domain.DataError
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.repository.ExerciseRepository
import com.mrapps.domain.Result
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.model.exercise_type.ExerciseType
import com.mrapps.domain.util.DispatcherProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ExerciseRepositoryImpl @Inject constructor(
    private val exerciseDao: ExerciseDao,
    private val strengthExerciseDao: StrengthExerciseDao,
    private val dispatchers: DispatcherProvider
) : ExerciseRepository {

    override suspend fun isExerciseNameTaken(name: String): Result<Boolean, DataError.Local> {
        return safeDatabaseOperation<Boolean, ExerciseRepositoryImpl> {
            exerciseDao.isExerciseNameTaken(name)
        }
    }

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
                        Result.Error(DataError.Local.UNKNOWN)
                    }
                }
            }

            is Result.Error -> Result.Error(getExerciseResult.error)
        }
    }

    override suspend fun updateExercise(exercise: Exercise): Result<Unit, DataError.Local> {
        return when (exercise.type) {
            is ExerciseType.Strength -> updateStrengthExercise(exercise)
            is ExerciseType.Endurance -> Result.Error(DataError.Local.UNKNOWN)
        }
    }

    private suspend fun updateStrengthExercise(exercise: Exercise): Result<Unit, DataError.Local> {
        val mappingResult =
            safeMappingOperation<Pair<ExerciseEntity, StrengthExerciseEntity>, ExerciseRepositoryImpl> {
                exercise.toExerciseWithStrengthEntities()
            }

        val (exerciseEntity, strengthEntity) = when (mappingResult) {
            is Result.Success -> mappingResult.data
            is Result.Error -> return Result.Error(mappingResult.error)
        }

        return safeDatabaseOperation<Unit, ExerciseRepositoryImpl> {
            exerciseDao.updateExerciseWithStrengthExercise(
                exercise = exerciseEntity,
                strengthExercise = strengthEntity
            )
        }
    }

    private suspend fun getStrengthExercise(exerciseEntity: ExerciseEntity): Result<Exercise, DataError.Local> {
        val getStrengthExerciseResult =
            safeDatabaseOperation<StrengthExerciseEntity, ExerciseRepositoryImpl> {
                strengthExerciseDao.getStrengthExerciseById(exerciseEntity.id)
            }

        return when (getStrengthExerciseResult) {
            is Result.Success -> {
                val exercise = ExerciseWithStrengthExercise(
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

    override suspend fun addStrengthExercise(exercise: Exercise): Result<Unit, DataError.Local> {
        val mappingResult =
            safeMappingOperation<Pair<ExerciseEntity, StrengthExerciseEntity>, ExerciseRepositoryImpl> {
                exercise.toExerciseWithStrengthEntities()
            }

        val (exerciseEntity, strengthEntity) = when (mappingResult) {
            is Result.Success -> mappingResult.data
            is Result.Error -> return Result.Error(mappingResult.error)
        }

        return safeDatabaseOperation<Unit, ExerciseRepositoryImpl> {
            exerciseDao.insertExerciseWithStrengthExercise(
                exercise = exerciseEntity,
                strengthExercise = strengthEntity
            )
        }
    }

    override suspend fun getStrengthExercises(): Result<List<Exercise>, DataError.Local> {
        val getResult =
            safeDatabaseOperation<List<ExerciseWithStrengthExercise>, ExerciseRepositoryImpl> {
                exerciseDao.getExerciseWithStrengthExerciseList()
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
            exerciseDao.observeExerciseWithStrengthExerciseList().map { entityList ->
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