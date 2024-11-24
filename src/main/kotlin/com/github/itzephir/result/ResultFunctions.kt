package com.github.itzephir.result

/**
 * Executes the given [action] if this [Result] is a success.
 *
 * @param action The action to perform on the data of this success result.
 * @return The original [Result].
 */
public inline fun <D, E : Error> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E> =
    apply { (this as? Result.Success)?.data?.let(action) }

/**
 * Executes the given [action] if this [Result] is a [Result.Failure] containing an error
 * of type [E], where [E] is a subtype of [RootE].
 *
 * @param action The action to be executed with the error of type [E] as the argument.
 * @return This [Result] instance.
 */
public inline fun <reified E : RootE, RootE : Error> Result<*, RootE>.onFailure(action: (E) -> Unit): Result<*, RootE> =
    apply { (this as? Result.Failure)?.error?.let { (it as? E)?.let(action) } }

/**
 * Transforms the data of this success result using the given [transform] function and wraps it in a new result.
 * If this result is a failure, the original error is preserved.
 *
 * @param transform The transformation function to apply on the data of this success result.
 * @return A new [Result] with the transformed data or the original error.
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

/**
 * Converts a Kotlin [kotlin.Result] to a custom [Result], mapping any throwable to the custom error type using [throwableMap].
 *
 * @param throwableMap The function to convert a [Throwable] to the custom error type [E].
 * @return The converted [Result].
 */
public fun <D, E : Error> kotlin.Result<D>.toResult(throwableMap: (Throwable) -> E): Result<D, E> =
    Result.Success(getOrElse { return Result.Failure(throwableMap(it)) })

/**
 * Converts a custom [Result] to a Kotlin [kotlin.Result], mapping any custom error to a throwable using [errorMap].
 *
 * @param errorMap The function to convert the custom error type [E] to a [Throwable].
 * @return The converted [kotlin.Result].
 */
public fun <D, E : Error> Result<D, E>.toKotlinResult(errorMap: (E) -> Throwable): kotlin.Result<D> {
    return kotlin.Result.success(getOrElse { return kotlin.Result.failure(errorMap(it)) })
}

/**
 * Converts a custom [Result] with a throwable error to a Kotlin [kotlin.Result].
 *
 * @return The converted [kotlin.Result].
 */
public fun <D, E : ErrorWithThrowable> Result<D, E>.toKotlinResult(): kotlin.Result<D> {
    return kotlin.Result.success(getOrElse { return kotlin.Result.failure(it.throwable) })
}