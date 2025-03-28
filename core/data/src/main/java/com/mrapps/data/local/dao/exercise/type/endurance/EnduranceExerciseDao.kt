package com.mrapps.data.local.dao.exercise.type.endurance

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.mrapps.data.local.dao.exercise.BaseExerciseDao
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity
import com.mrapps.data.local.relation.ExerciseWithType
import kotlinx.coroutines.flow.Flow

@Dao
interface EnduranceExerciseDao : BaseExerciseDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    override suspend fun insertExercise(exercise: ExerciseEntity)

    @Update
    override suspend fun updateExercise(exercise: ExerciseEntity)

    @Query("DELETE FROM exercise_entity WHERE id = :id")
    override suspend fun deleteExerciseById(id: String)

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertEnduranceExercise(exercise: EnduranceExerciseEntity)

    @Update
    suspend fun updateEnduranceExercise(exercise: EnduranceExerciseEntity)

    @Query("SELECT * FROM endurance_exercise_entity WHERE exercise_id = :id")
    suspend fun getEnduranceExerciseById(id: String): EnduranceExerciseEntity

    @Transaction
    @Query("SELECT * FROM exercise_entity WHERE id IN (SELECT exercise_id FROM endurance_exercise_entity)")
    suspend fun getAllEnduranceExercises(): List<ExerciseWithType.Endurance>

    @Transaction
    @Query("SELECT * FROM exercise_entity WHERE id IN (SELECT exercise_id FROM endurance_exercise_entity)")
    fun observeEnduranceExercises(): Flow<List<ExerciseWithType.Endurance>>

    @Transaction
    suspend fun insertExerciseWithEnduranceExercise(
        exercise: ExerciseEntity,
        enduranceExercise: EnduranceExerciseEntity
    ) {
        insertExercise(exercise)
        insertEnduranceExercise(enduranceExercise)
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
    suspend fun removeSavedExerciseAndAddNewEnduranceExercise(
        exercise: ExerciseEntity,
        enduranceExercise: EnduranceExerciseEntity
    ) {
        deleteExerciseById(exercise.id)
        insertExerciseWithEnduranceExercise(exercise, enduranceExercise)
    }
}