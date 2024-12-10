//   Copyright 2024 Dvoryannikov Dmitriy
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package io.github.itzephir.result.extensions

import io.github.itzephir.result.models.Error
import io.github.itzephir.result.models.ErrorWithThrowable
import io.github.itzephir.result.models.Result

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