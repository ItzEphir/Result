package io.github.itzephir.result.extensions

import io.github.itzephir.result.models.Error
import io.github.itzephir.result.models.Result

@Suppress("FunctionName")
public fun <D, E : Error> Result.Companion.Success(data: D): Result<D, E> = Result<D, E>.Success<D>(data)

public fun <D, E : Error> Result.Companion.success(data: D): Result<D, E> = Result.Companion.Success<D, E>(data)

@Suppress("FunctionName")
public fun <D, E : Error> Result.Companion.Failure(error: E): Result<D, E> = Result<D, E>.Failure<E>(error)

public fun <D, E : Error> Result.Companion.failure(error: E): Result<D, E> = Result.Companion.Failure<D, E>(error)