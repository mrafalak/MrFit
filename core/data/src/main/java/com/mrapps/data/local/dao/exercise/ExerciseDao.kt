package com.mrapps.data.local.dao.exercise

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithType
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertExercise(exercise: ExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertStrengthExercise(exercise: StrengthExerciseEntity)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEnduranceExercise(exercise: EnduranceExerciseEntity)

    @Update
    suspend fun updateExercise(exercise: ExerciseEntity)

    @Update
    suspend fun updateStrengthExercise(exercise: StrengthExerciseEntity)

    @Update
    suspend fun updateEnduranceExercise(exercise: EnduranceExerciseEntity)

    @Query("SELECT * FROM exercise_entity ORDER BY name ASC")
    suspend fun getAllExercises(): List<ExerciseEntity>

    @Query("SELECT * FROM exercise_entity WHERE id = :id")
    suspend fun getExerciseById(id: String): ExerciseEntity

    @Query("SELECT * FROM exercise_entity WHERE name = :name")
    suspend fun getExerciseByName(name: String): ExerciseEntity?

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
    suspend fun insertExerciseWithEnduranceExercise(
        exercise: ExerciseEntity,
        enduranceExercise: EnduranceExerciseEntity
    ) {
        insertExercise(exercise)
        insertEnduranceExercise(enduranceExercise)
    }

    @Transaction
    @Query("SELECT * FROM exercise_entity WHERE id IN (SELECT exercise_id FROM strength_exercise_entity)")
    suspend fun getAllStrengthExercises(): List<ExerciseWithType.Strength>

    @Transaction
    @Query("SELECT * FROM exercise_entity WHERE id IN (SELECT exercise_id FROM endurance_exercise_entity)")
    suspend fun getAllEnduranceExercises(): List<ExerciseWithType.Endurance>

    @Transaction
    @Query("SELECT * FROM exercise_entity WHERE id IN (SELECT exercise_id FROM strength_exercise_entity)")
    fun observeStrengthExercises(): Flow<List<ExerciseWithType.Strength>>

    @Transaction
    @Query("SELECT * FROM exercise_entity WHERE id IN (SELECT exercise_id FROM endurance_exercise_entity)")
    fun observeEnduranceExercises(): Flow<List<ExerciseWithType.Endurance>>

    @Transaction
    suspend fun updateExerciseWithStrengthExercise(
        exercise: ExerciseEntity,
        strengthExercise: StrengthExerciseEntity
    ) {
        updateExercise(exercise)
        updateStrengthExercise(strengthExercise)
    }

    @Transaction
    suspend fun updateExerciseWithEnduranceExercise(
        exercise: ExerciseEntity,
        enduranceExercise: EnduranceExerciseEntity
    ) {
        updateExercise(exercise)
        updateEnduranceExercise(enduranceExercise)
    }

    @Transaction
    suspend fun removeSavedExerciseAndAddNewStrengthExercise(
        exercise: ExerciseEntity,
        strengthExercise: StrengthExerciseEntity
    ) {
        deleteExerciseById(exercise.id)
        insertExerciseWithStrengthExercise(exercise, strengthExercise)
    }

    @Transaction
    suspend fun removeSavedExerciseAndAddNewEnduranceExercise(
        exercise: ExerciseEntity,
        enduranceExercise: EnduranceExerciseEntity
    ) {
        deleteExerciseById(exercise.id)
        insertExerciseWithEnduranceExercise(exercise, enduranceExercise)
    }
}