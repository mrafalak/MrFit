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

    suspend fun validateName(
        name: String,
        edit: Boolean = false,
        editedExerciseId: String? = null
    ): Result<Unit, ExerciseError.Name> {
        return when {
            name.isBlank() -> Result.Error(ExerciseError.Name.EMPTY)
            name.length > MAX_NAME_LENGTH -> Result.Error(ExerciseError.Name.TOO_LONG)
            else -> checkNameDuplicate(name, edit, editedExerciseId)
        }
    }

    private suspend fun checkNameDuplicate(
        name: String,
        edit: Boolean,
        editedExerciseId: String?
    ): Result<Unit, ExerciseError.Name> {
        return when (val result = exerciseRepository.getExerciseByName(name)) {
            is Result.Success -> {
                val savedExerciseWithEnteredName = result.data

                if (savedExerciseWithEnteredName == null) {
                    Result.Success(Unit)
                } else if (edit) {
                    if (savedExerciseWithEnteredName.id == editedExerciseId) {
                        Result.Success(Unit)
                    } else {
                        Result.Error(ExerciseError.Name.DUPLICATED)
                    }
                } else {
                    Result.Error(ExerciseError.Name.DUPLICATED)
                }
            }

            is Result.Error -> Result.Error(ExerciseError.Name.UNKNOWN)
        }
    }

    fun validateDescription(description: String): Result<Unit, ExerciseError.Description> {
        return when {
            description.length > MAX_DESCRIPTION_LENGTH -> Result.Error(ExerciseError.Description.TOO_LONG)
            else -> Result.Success(Unit)
        }
    }
}