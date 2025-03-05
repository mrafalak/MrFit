package com.mrapps.data.local.entity.training

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.ExerciseExecutionEntity

@Entity(
    tableName = "training_exercise_entity",
    foreignKeys = [
        ForeignKey(
            entity = TrainingSessionEntity::class,
            parentColumns = ["id"],
            childColumns = ["training_session_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.SET_NULL
        ),
        ForeignKey(
            entity = ExerciseExecutionEntity::class,
            parentColumns = ["id"],
            childColumns = ["exercise_execution_id"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [
        Index(value = ["training_session_id"]),
        Index(value = ["exercise_id"]),
        Index(value = ["exercise_execution_id"])
    ]
)
data class TrainingExerciseEntity(

    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,

    @ColumnInfo(name = "training_session_id") val trainingSessionId: String,

    @ColumnInfo(name = "exercise_id") val exerciseId: String?,

    @ColumnInfo(name = "exercise_execution_id") val exerciseExecutionId: String?,

    @ColumnInfo(name = "order") val order: Int,

    @ColumnInfo(name = "notes") val notes: String?
)