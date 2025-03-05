package com.mrapps.data.local.entity.exercise.type.strength

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrapps.data.local.entity.exercise.ExerciseExecutionEntity
import com.mrapps.domain.model.exercise.type.strength.SetType
import java.time.Duration

@Entity(
    tableName = "strength_set_entity",
    foreignKeys = [
        ForeignKey(
            entity = ExerciseExecutionEntity::class,
            parentColumns = ["id"],
            childColumns = ["exercise_execution_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["exercise_execution_id"])]
)
data class StrengthSetEntity(

    @PrimaryKey val id: String,

    @ColumnInfo(name = "exercise_execution_id") val exerciseExecutionId: String,

    @ColumnInfo(name = "set_type") val setType: SetType,

    @ColumnInfo(name = "set_number") val setNumber: Int,

    @ColumnInfo(name = "reps") val reps: Int?,

    @ColumnInfo(name = "weight") val weight: Double?,

    @ColumnInfo(name = "duration") val duration: Duration?,

    @ColumnInfo(name = "rest_time") val restTime: Int?,

    @ColumnInfo(name = "notes") val notes: String?
)