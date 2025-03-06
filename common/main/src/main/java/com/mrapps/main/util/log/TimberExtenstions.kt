package com.mrapps.main.util.log

import timber.log.Timber

inline fun <reified T> Timber.Forest.debug(
    message: String,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).d(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.info(
    message: String,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).i(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.warn(
    message: String,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).w(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.error(
    message: String,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).e(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.verbose(
    message: String,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).v(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.wtf(
    message: String,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).wtf(throwable, formatMessageWithArgs(message, *args))
}

fun formatMessageWithArgs(message: String, vararg args: Any?): String {
    return try {
        val expectedArgsCount = "%[a-z]".toRegex().findAll(message).count()
        if (args.size != expectedArgsCount) {
            "Error: Expected $expectedArgsCount arguments, but got ${args.size}. Message: '$message'"
        } else {
            String.format(message, *args)
        }
    } catch (e: Exception) {
        "Error formatting message: '$message'. Arguments: ${args.joinToString()}"
    }
}