package com.mrapps.domain.repository

import com.mrapps.domain.DataError
import com.mrapps.domain.Result
import com.mrapps.domain.model.Exercise
import com.mrapps.domain.model.exercise.ExerciseTypeEnum
import kotlinx.coroutines.flow.Flow

interface ExerciseRepository {
    suspend fun getExerciseById(id: String): Result<Exercise, DataError.Local>
    suspend fun getExerciseByName(name: String): Result<Exercise?, DataError.Local>
    suspend fun updateExercise(exercise: Exercise): Result<Unit, DataError.Local>
    suspend fun addExercise(exercise: Exercise): Result<Unit, DataError.Local>
    suspend fun getExercisesByType(type: ExerciseTypeEnum): Result<List<Exercise>, DataError.Local>
    fun observeExercises(type: ExerciseTypeEnum): Flow<Result<List<Exercise>, DataError.Local>>
    suspend fun removeExerciseById(id: String): Result<Unit, DataError.Local>
}