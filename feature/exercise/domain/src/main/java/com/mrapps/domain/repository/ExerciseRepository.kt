package com.mrapps.domain.repository

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun isExerciseNameTaken(name: String): Result<Boolean, DataError.Local>
    suspend fun getExerciseById(id: String): Result<Exercise, DataError.Local>
    suspend fun updateExercise(exercise: Exercise): Result<Unit, DataError.Local>
    suspend fun addStrengthExercise(exercise: Exercise): Result<Unit, DataError.Local>
    suspend fun getStrengthExercises(): Result<List<Exercise>, DataError.Local>
    fun observeStrengthExercises(): Flow<Result<List<Exercise>, DataError.Local>>
    suspend fun removeExerciseById(id: String): Result<Unit, DataError.Local>
}