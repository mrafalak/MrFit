package com.mrapps.domain.use_case

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.repository.ExerciseRepository
import javax.inject.Inject

class RemoveExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend fun invoke(id: String): Result<Unit, DataError.Local> {
        return repository.removeExerciseById(id)
    }
}