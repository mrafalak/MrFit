package com.mrapps.data.local.dao.training

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mrapps.data.local.entity.training.TrainingSessionEntity
import com.mrapps.domain.model.training.TrainingSessionTypeEnum

@Dao
interface TrainingSessionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTrainingSession(trainingSession: TrainingSessionEntity)

    @Query("SELECT * FROM training_session_entity ORDER BY date DESC")
    fun getAllTrainingSessions(): List<TrainingSessionEntity>

    @Query("SELECT * FROM training_session_entity WHERE type = :type ORDER BY date DESC")
    fun getTrainingSessionsByType(type: TrainingSessionTypeEnum): List<TrainingSessionEntity>

    @Update
    suspend fun updateTrainingSession(trainingSession: TrainingSessionEntity)

    @Query("DELETE FROM training_session_entity WHERE id = :id")
    suspend fun deleteTrainingSessionById(id: String)

    @Query("DELETE FROM training_session_entity")
    suspend fun deleteAllTrainingSession()
}