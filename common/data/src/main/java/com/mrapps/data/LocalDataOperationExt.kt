package com.mrapps.data

import android.database.sqlite.*
import com.mrapps.domain.DataError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import timber.log.Timber
import com.mrapps.domain.Result
import com.mrapps.util.error
import com.mrapps.util.warn
import java.io.FileNotFoundException
import java.io.IOException

inline fun <T, reified R> safeDatabaseOperation(
    operation: () -> T
): Result<T, DataError.Local> {
    return try {
        Result.Success(operation())
    } catch (e: SQLiteException) {
        logDatabaseError<R>(e)
        Result.Error(mapExceptionToDataError(e))
    } catch (e: IOException) {
        logDatabaseError<R>(e)
        Result.Error(mapExceptionToDataError(e))
    } catch (e: SecurityException) {
        logDatabaseError<R>(e)
        Result.Error(mapExceptionToDataError(e))
    } catch (e: Exception) {
        logDatabaseError<R>(e)
        Result.Error(DataError.Local.UNKNOWN)
    }
}

inline fun <T, reified R> safeDatabaseFlowOperation(
    dispatcher: CoroutineDispatcher,
    operation: () -> Flow<T>
): Flow<Result<T, DataError.Local>> {
    return operation()
        .map<T, Result<T, DataError.Local>> { data ->
            Result.Success(data)
        }
        .catch { e ->
            logDatabaseError<R>(e)
            val error = mapExceptionToDataError(e)
            emit(Result.Error(error))
        }
        .flowOn(dispatcher)
}

inline fun <reified R> logDatabaseError(e: Throwable) {
    try {
        Timber.error<R>("Database operation failed: ${e.message}", throwable = e)
    } catch (loggingException: Exception) {
        Timber.warn<R>("Failed to log error with Timber", loggingException)
    }
}

fun mapExceptionToDataError(e: Throwable): DataError.Local {
    return when (e) {
        is SQLiteFullException -> DataError.Local.DISK_FULL
        is SQLiteConstraintException, is SQLiteDatabaseCorruptException -> DataError.Local.DATA_INCONSISTENCY
        is FileNotFoundException -> DataError.Local.FILE_NOT_FOUND
        is SecurityException -> DataError.Local.PERMISSION_DENIED
        is SQLiteException -> DataError.Local.DATABASE_ERROR
        is IOException -> DataError.Local.IO_ERROR
        else -> DataError.Local.UNKNOWN
    }
}