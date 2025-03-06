package com.mrapps.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity

data class ExerciseWithStrengthExercise(
    @Embedded val exercise: ExerciseEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "exercise_id"
    )
    val strengthExercise: StrengthExerciseEntity
)