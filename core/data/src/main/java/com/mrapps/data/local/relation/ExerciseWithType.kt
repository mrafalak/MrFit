package com.mrapps.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.data.local.entity.exercise.type.EnduranceExerciseEntity
import com.mrapps.data.local.entity.exercise.type.StrengthExerciseEntity

sealed class ExerciseWithType(@Embedded open val exercise: ExerciseEntity) {
    data class Strength(
        @Embedded override val exercise: ExerciseEntity,
        @Relation(
            parentColumn = "id",
            entityColumn = "exercise_id"
        )
        val strengthExercise: StrengthExerciseEntity
    ) : ExerciseWithType(exercise)

    data class Endurance(
        @Embedded override val exercise: ExerciseEntity,
        @Relation(
            parentColumn = "id",
            entityColumn = "exercise_id"
        )
        val enduranceExercise: EnduranceExerciseEntity
    ) : ExerciseWithType(exercise)
}