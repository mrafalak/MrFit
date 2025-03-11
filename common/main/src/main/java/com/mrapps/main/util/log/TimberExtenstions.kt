package com.mrapps.main.util.log

import timber.log.Timber

private const val DEFAULT_ERROR_MESSAGE = "Unknown error occurred"

inline fun <reified T> Timber.Forest.debug(
    message: String? = null,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).d(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.info(
    message: String? = null,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).i(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.warn(
    message: String? = null,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).w(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.error(
    message: String? = null,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).e(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.verbose(
    message: String? = null,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).v(throwable, formatMessageWithArgs(message, *args))
}

inline fun <reified T> Timber.Forest.wtf(
    message: String? = null,
    vararg args: Any?,
    throwable: Throwable? = null
) {
    tag(T::class.java.simpleName).wtf(throwable, formatMessageWithArgs(message, *args))
}

fun formatMessageWithArgs(message: String?, vararg args: Any?): String {
    val nonEmptyMessage = message.takeUnless { it.isNullOrBlank() } ?: DEFAULT_ERROR_MESSAGE

    return try {
        val expectedArgsCount = "%[a-z]".toRegex().findAll(nonEmptyMessage).count()
        if (args.size != expectedArgsCount) {
            "Error: Expected $expectedArgsCount arguments, but got ${args.size}. Message: '$nonEmptyMessage'"
        } else {
            String.format(nonEmptyMessage, *args)
        }
    } catch (e: Exception) {
        "Error formatting message: '$nonEmptyMessage'. Arguments: ${args.joinToString()}"
    }
}