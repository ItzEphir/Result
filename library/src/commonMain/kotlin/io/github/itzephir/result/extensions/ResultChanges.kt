package io.github.itzephir.result.extensions

import io.github.itzephir.result.models.Error
import io.github.itzephir.result.models.Result

/**
 * Transforms the data of this success result using the given [transform] function and wraps it in a new result.
 * If this result is a failure, the original error is preserved.
 *
 * @param transform The transformation function to apply on the data of this success result.
 * @return A new [io.github.itzephir.result.models.Result] with the transformed data or the original error.
 */
public inline fun <D, R, E : Error> Result<D, E>.map(transform: (D) -> R): Result<R, E> =
    when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Failure -> Result.Failure(error)
    }

/**
 * Flattens a nested result structure to a single result.
 * If this result is a success, the contained result is returned.
 * If this result is a failure, the original error is preserved.
 *
 * @return The flattened [Result].
 */
public fun <D, E : Error> Result<Result<D, E>, E>.flat(): Result<D, E> =
    when (this) {
        is Result.Success -> this.data
        is Result.Failure -> Result.Failure(error)
    }

/**
 * Transforms the data of this success result using the given [transform] function to produce a new result,
 * and then flattens it. If this result is a failure, the original error is preserved.
 *
 * @param transform The transformation function to apply on the data to produce a new [Result].
 * @return The transformed and flattened [Result].
 */
public inline fun <D, R, E : Error> Result<D, E>.flatMap(transform: (D) -> Result<R, E>): Result<R, E> =
    map(transform).flat()