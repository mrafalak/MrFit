package com.mrapps.domain.use_case

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.repository.ExerciseRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ObserveExerciseListUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    fun invoke(): Flow<Result<List<Exercise>, DataError.Local>> {
        return repository.observeStrengthExercises()
    }
}