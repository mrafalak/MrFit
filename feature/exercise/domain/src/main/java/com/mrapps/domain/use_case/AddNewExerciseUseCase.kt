package com.mrapps.domain.use_case

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.repository.ExerciseRepository
import javax.inject.Inject

class AddNewExerciseUseCase @Inject constructor(
    private val repository: ExerciseRepository
) {
    suspend fun invoke(exercise: Exercise): Result<Unit, DataError.Local> {
        return repository.addExercise(exercise)
    }
}