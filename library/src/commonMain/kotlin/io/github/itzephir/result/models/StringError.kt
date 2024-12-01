package io.github.itzephir.result.models

import io.github.itzephir.result.extensions.Failure

public interface StringError : Error {
    public val message: String
}

public data class DefaultStringError(public override val message: String) : StringError

@Suppress("FunctionName")
public fun <D> Result.Companion.Failure(message: String): Result<D, StringError> =
    Result.Failure<D, StringError>(DefaultStringError(message))
