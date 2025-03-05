package com.mrapps.data.local.dao.training

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrapps.data.local.entity.training.TrainingExerciseEntity

@Dao
interface TrainingExerciseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingExercise(trainingExercise: TrainingExerciseEntity)

    @Query("SELECT * FROM training_exercise_entity WHERE training_session_id = :trainingSessionId ORDER BY `order` ASC")
    fun getTrainingExercisesBySessionId(trainingSessionId: String): List<TrainingExerciseEntity>

    @Update
    suspend fun updateTrainingExercise(trainingExercise: TrainingExerciseEntity)

    @Query("DELETE FROM training_exercise_entity WHERE id = :id")
    suspend fun deleteTrainingExerciseById(id: String)

    @Query("DELETE FROM training_exercise_entity WHERE training_session_id = :id")
    suspend fun deleteTrainingExerciseByTrainingSessionId(id: String)
}