package com.mrapps.data.local.entity.exercise

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrapps.domain.model.exercise.ExerciseTypeEnum

@Entity(tableName = "exercise_entity")
data class ExerciseEntity(

    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "type") val type: ExerciseTypeEnum,

    @ColumnInfo(name = "description") val description: String?
)