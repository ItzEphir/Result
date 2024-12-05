package io.github.itzephir.result.extensions

import io.github.itzephir.result.models.Result
import io.github.itzephir.result.models.Result.Success
import io.github.itzephir.result.models.Error
import io.github.itzephir.result.models.Result.Failure

/**
 * Executes the given [action] if this [Result] is a success.
 *
 * @param action The action to perform on the data of this success result.
 * @return The original [Result].
 */
public inline fun <D, E : Error> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> =
    apply { (this as? Success)?.data?.let(action) }

/**
 * Executes the given [action] if this [Result] is a [Result.Failure].
 *
 * @param action The action to be executed with the error of type [E] as the argument.
 * @return This [Result] instance.
 */
public inline fun <D, E : Error> Result<D, E>.onFailure(action: (E) -> Unit): Result<D, E> =
    also { (it as? Failure)?.error?.let(action) }
