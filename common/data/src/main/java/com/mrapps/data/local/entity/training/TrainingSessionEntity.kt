package com.mrapps.data.local.entity.training

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mrapps.domain.model.training.TrainingSessionTypeEnum
import java.time.Duration
import java.time.LocalDateTime

@Entity(tableName = "training_session_entity")
data class TrainingSessionEntity(

    @PrimaryKey
    @ColumnInfo(name = "id") val id: String,

    @ColumnInfo(name = "name") val name: String,

    @ColumnInfo(name = "type") val type: TrainingSessionTypeEnum,

    @ColumnInfo(name = "date") val date: LocalDateTime,

    @ColumnInfo(name = "description") val description: String?,

    @ColumnInfo(name = "duration") val duration: Duration?
)