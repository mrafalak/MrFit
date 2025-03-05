package com.mrapps.data.local

import androidx.room.TypeConverter
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class Converters {

    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    @TypeConverter
    fun fromTimestamp(value: Long?): LocalDateTime? {
        return value?.let { LocalDateTime.ofEpochSecond(it, 0, ZoneOffset.UTC) }
    }

    @TypeConverter
    fun dateToTimestamp(date: LocalDateTime?): Long? {
        return date?.toEpochSecond(ZoneOffset.UTC)
    }

    @TypeConverter
    fun fromSeconds(value: Long?): Duration? {
        return value?.let { Duration.ofSeconds(it) }
    }

    @TypeConverter
    fun durationToSeconds(duration: Duration?): Long? {
        return duration?.seconds
    }
}