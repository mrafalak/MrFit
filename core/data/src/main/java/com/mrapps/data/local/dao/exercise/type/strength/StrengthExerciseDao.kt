package com.mrapps.data.local.dao.exercise.type.strength

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mrapps.data.local.dao.exercise.BaseExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithType
import kotlinx.coroutines.flow.Flow

@Dao
interface StrengthExerciseDao : BaseExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insertExercise(exercise: ExerciseEntity)

    @Update
    override suspend fun updateExercise(exercise: ExerciseEntity)

    @Query("DELETE FROM exercise_entity WHERE id = :id")
    override suspend fun deleteExerciseById(id: String)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStrengthExercise(exercise: StrengthExerciseEntity)

    @Update
    suspend fun updateStrengthExercise(exercise: StrengthExerciseEntity)

    @Query("SELECT * FROM strength_exercise_entity WHERE exercise_id = :id")
    suspend fun getStrengthExerciseById(id: String): StrengthExerciseEntity

    @Transaction
    @Query("SELECT * FROM exercise_entity WHERE id IN (SELECT exercise_id FROM strength_exercise_entity)")
    suspend fun getAllStrengthExercises(): List<ExerciseWithType.Strength>

    @Transaction
    @Query("SELECT * FROM exercise_entity WHERE id IN (SELECT exercise_id FROM strength_exercise_entity)")
    fun observeStrengthExercises(): Flow<List<ExerciseWithType.Strength>>

    @Transaction
    suspend fun insertExerciseWithStrengthExercise(
        exercise: ExerciseEntity,
        strengthExercise: StrengthExerciseEntity
    ) {
        insertExercise(exercise)
        insertStrengthExercise(strengthExercise)
    }

    @Transaction
    suspend fun updateExerciseWithStrengthExercise(
        exercise: ExerciseEntity,
        strengthExercise: StrengthExerciseEntity
    ) {
        updateExercise(exercise)
        updateStrengthExercise(strengthExercise)
    }

    @Transaction
    suspend fun removeSavedExerciseAndAddNewStrengthExercise(
        exercise: ExerciseEntity,
        strengthExercise: StrengthExerciseEntity
    ) {
        deleteExerciseById(exercise.id)
        insertExerciseWithStrengthExercise(exercise, strengthExercise)
    }
}