package com.mrapps.data.local.dao.exercise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithStrengthExercise
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStrengthExercise(exercise: StrengthExerciseEntity)

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Update
    suspend fun updateStrengthExercise(exercise: StrengthExerciseEntity)

    @Query("SELECT * FROM exercise_entity ORDER BY name ASC")
    suspend fun getAllExercises(): List<ExerciseEntity>

    @Query("SELECT * FROM exercise_entity WHERE id = :id")
    suspend fun getExerciseById(id: String): ExerciseEntity

    @Query("DELETE FROM exercise_entity WHERE id = :id")
    suspend fun deleteExerciseById(id: String)

    @Transaction
    suspend fun insertExerciseWithStrengthExercise(
        exercise: ExerciseEntity,
        strengthExercise: StrengthExerciseEntity
    ) {
        insertExercise(exercise)
        insertStrengthExercise(strengthExercise)
    }

    @Transaction
    @Query("SELECT * FROM exercise_entity ORDER BY name ASC")
    suspend fun getExerciseWithStrengthExerciseList(): List<ExerciseWithStrengthExercise>

    @Transaction
    @Query("SELECT * FROM exercise_entity ORDER BY name ASC")
    fun observeExerciseWithStrengthExerciseList(): Flow<List<ExerciseWithStrengthExercise>>

    @Transaction
    suspend fun updateExerciseWithStrengthExercise(
        exercise: ExerciseEntity,
        strengthExercise: StrengthExerciseEntity
    ) {
        updateExercise(exercise)
        updateStrengthExercise(strengthExercise)
    }

    @Query("SELECT EXISTS(SELECT 1 FROM exercise_entity WHERE name = :name)")
    suspend fun isExerciseNameTaken(name: String): Boolean
}