package com.mrapps.domain.use_case

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import com.mrapps.domain.repository.ExerciseRepository
import com.mrapps.main.util.log.error
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import timber.log.Timber
import javax.inject.Inject

class ObserveExerciseListUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    fun invoke(): Flow<Result<List<Exercise>, DataError.Local>> {
        val exerciseFlows = listOf(
            repository.observeExercises(ExerciseTypeEnum.STRENGTH),
            repository.observeExercises(ExerciseTypeEnum.ENDURANCE)
        )

        return combineMultipleFlows(exerciseFlows)
    }

    private fun combineMultipleFlows(
        flows: List<Flow<Result<List<Exercise>, DataError.Local>>>
    ): Flow<Result<List<Exercise>, DataError.Local>> {
        return combine(flows) { results ->
            val allExercises = mutableListOf<Exercise>()

            results.forEach { result ->
                when (result) {
                    is Result.Success -> allExercises.addAll(result.data)
                    is Result.Error -> return@combine Result.Error<List<Exercise>, DataError.Local>(
                        result.error
                    )
                }
            }

            Result.Success(allExercises)
        }.catch { e ->
            Timber.error<ObserveExerciseListUseCase>(e.message, e)
            emit(Result.Error(DataError.Local.UNKNOWN))
        }
    }
}