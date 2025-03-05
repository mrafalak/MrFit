package com.mrapps.data.local.entity.exercise.type.strength

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.domain.model.exercise.type.strength.ExerciseGoal
import com.mrapps.domain.model.exercise.type.strength.MovementType
import com.mrapps.domain.model.exercise.type.strength.MuscleGroup

@Entity(
    tableName = "strength_exercise_entity",
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
data class StrengthExerciseEntity(

    @PrimaryKey
    @ColumnInfo(name = "exercise_id") val exerciseId: String,

    @ColumnInfo(name = "movement_type") val movementType: MovementType,

    @ColumnInfo(name = "muscle_group") val muscleGroup: MuscleGroup,

    @ColumnInfo(name = "exercise_goal") val exerciseGoal: ExerciseGoal
)