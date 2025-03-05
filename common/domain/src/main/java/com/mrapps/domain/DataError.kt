package com.mrapps.domain

sealed interface DataError : Error {

    enum class Local : DataError {
        DISK_FULL,
        DATA_INCONSISTENCY,
        FILE_NOT_FOUND,
        PERMISSION_DENIED,
        DATABASE_ERROR,
        IO_ERROR,
        UNKNOWN
    }
}