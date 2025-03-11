package com.mrapps.domain.validator

import com.mrapps.domain.Result
import com.mrapps.domain.error.ExerciseError
import com.mrapps.domain.repository.ExerciseRepository
import javax.inject.Inject

class ExerciseValidator @Inject constructor(
    private val exerciseRepository: ExerciseRepository
) {
    companion object {
        const val MAX_NAME_LENGTH = 50
        const val MAX_DESCRIPTION_LENGTH = 1000
    }

    suspend fun validateName(name: String): Result<Unit, ExerciseError.Name> {
        return when {
            name.isBlank() -> Result.Error(ExerciseError.Name.EMPTY)
            name.length > MAX_NAME_LENGTH -> Result.Error(ExerciseError.Name.TOO_LONG)
            else -> {
                return when (val result = exerciseRepository.isExerciseNameTaken(name)) {
                    is Result.Error -> Result.Error(ExerciseError.Name.UNKNOWN)

                    is Result.Success -> {
                        val isExerciseNameTaken = result.data
                        if (isExerciseNameTaken) {
                            Result.Error(ExerciseError.Name.DUPLICATED)
                        } else {
                            Result.Success(Unit)
                        }
                    }
                }
            }
        }
    }

    fun validateDescription(description: String): Result<Unit, ExerciseError.Description> {
        return when {
            description.length > MAX_DESCRIPTION_LENGTH -> Result.Error(ExerciseError.Description.TOO_LONG)
            else -> Result.Success(Unit)
        }
    }
}