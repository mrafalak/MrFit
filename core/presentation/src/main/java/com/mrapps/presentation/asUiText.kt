package com.mrapps.presentation

import com.mrapps.domain.DataError
import com.mrapps.domain.units.MeasurementUnit
import com.mrapps.mrfit.core.presentation.R

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
        DataError.Local.PERMISSION_DENIED -> UiText.StringResource(R.string.error_permission_denied)
        DataError.Local.FILE_NOT_FOUND -> UiText.StringResource(R.string.error_file_not_found)
        else -> UiText.StringResource(R.string.error_generic)
    }
}

fun MeasurementUnit.Time.asUiText(): UiText {
    return when (this) {
        MeasurementUnit.Time.Millisecond -> UiText.StringResource(R.string.time_unit_millisecond)
        MeasurementUnit.Time.Second -> UiText.StringResource(R.string.time_unit_second)
        MeasurementUnit.Time.Minute -> UiText.StringResource(R.string.time_unit_minute)
        MeasurementUnit.Time.Hour -> UiText.StringResource(R.string.time_unit_hour)
    }
}