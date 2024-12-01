package io.github.itzephir.result.extensions

import io.github.itzephir.result.models.Error
import io.github.itzephir.result.models.Result

/**
 * Returns the data of this success result.
 *
 * @return The data of this success result.
 * @throws ClassCastException if this result is a failure.
 */
public fun <D, E : Error> Result<D, E>.get(): D =
    (this as Result.Success).data

/**
 * Returns the data of this success result, or `null` if it is a failure.
 *
 * @return The data of this success result, or `null` if it is a failure.
 */
public fun <D, E : Error> Result<D, E>.getOrNull(): D? =
    (this as? Result.Success)?.data

/**
 * Returns the data of this success result, or the result of the given [action] if it is a failure.
 *
 * @param action The action to perform to provide a fallback data in case of failure.
 * @return The data of this success result, or the result of the [action] in case of failure.
 */
public inline fun <D, E : Error> Result<D, E>.getOrElse(action: (E) -> D): D =
    when (this) {
        is Result.Success -> data
        is Result.Failure -> action(error)
    }