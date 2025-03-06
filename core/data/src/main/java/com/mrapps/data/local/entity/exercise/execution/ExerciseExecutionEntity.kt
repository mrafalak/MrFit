package com.mrapps.data.local.entity.exercise.execution

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import java.time.Duration
import java.time.LocalDateTime

@Entity(
    tableName = "exercise_execution_entity",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseEntity::class,
            parentColumns = ["id"],
            childColumns = ["exercise_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["exercise_id"])]
)
data class ExerciseExecutionEntity(

    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,

    @ColumnInfo(name = "exercise_id") val exerciseId: String,

    @ColumnInfo(name = "date") val date: LocalDateTime?,

    @ColumnInfo(name = "duration") val duration: Duration?,

    @ColumnInfo(name = "rest_time") val restTime: Duration?,
)