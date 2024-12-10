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

@Suppress("FunctionName")
public fun <D, E : Error> Result.Companion.Success(data: D): Result<D, E> = Result.Success(data)

public fun <D, E : Error> Result.Companion.success(data: D): Result<D, E> = Result.Success<D, E>(data)

@Suppress("FunctionName")
public fun <D, E : Error> Result.Companion.Failure(error: E): Result<D, E> = Result.Failure(error)

public fun <D, E : Error> Result.Companion.failure(error: E): Result<D, E> = Result.Failure<D, E>(error)