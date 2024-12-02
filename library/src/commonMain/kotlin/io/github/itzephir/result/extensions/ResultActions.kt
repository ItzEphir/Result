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
import io.github.itzephir.result.models.Result

/**
 * Executes the given [action] if this [io.github.itzephir.result.models.Result] is a success.
 *
 * @param action The action to perform on the data of this success result.
 * @return The original [io.github.itzephir.result.models.Result].
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