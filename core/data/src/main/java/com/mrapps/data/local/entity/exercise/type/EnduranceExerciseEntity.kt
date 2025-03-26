package com.mrapps.data.local.entity.exercise.type

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mrapps.data.local.entity.exercise.ExerciseEntity
import com.mrapps.domain.model.exercise.type.endurance.EnduranceActivityType

@Entity(
    tableName = "endurance_exercise_entity",
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
data class EnduranceExerciseEntity(

    @PrimaryKey
    @ColumnInfo(name = "exercise_id") val exerciseId: String,

    @ColumnInfo(name = "activity_type") val activityType: EnduranceActivityType?,

    @ColumnInfo(name = "duration_unit_id") val durationUnitId: Int?,
)