package com.mrapps.presentation

import com.mrapps.domain.DataError
import com.mrapps.mrfit.core.presentation.R

fun DataError.asUiText(): UiText {
    return when (this) {
        DataError.Local.DISK_FULL -> UiText.StringResource(R.string.error_disk_full)
        DataError.Local.DATA_INCONSISTENCY -> UiText.StringResource(R.string.error_data_inconsistency)
        DataError.Local.UNKNOWN -> UiText.StringResource(R.string.error_unknown_local)
        DataError.Local.FILE_NOT_FOUND -> UiText.StringResource(R.string.error_file_not_found)
        DataError.Local.PERMISSION_DENIED -> UiText.StringResource(R.string.error_permission_denied)
        DataError.Local.DATABASE_ERROR -> UiText.StringResource(R.string.error_database_error)
        DataError.Local.IO_ERROR -> UiText.StringResource(R.string.error_io_error)
    }
}