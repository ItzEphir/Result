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

package io.github.itzephir.result.models

/**
 * A sealed interface representing the result of an operation, which can be either a success or a failure.
 *
 * @param <D> the type of the data in case of success.
 * @param <E> the type of the error in case of failure, must extend [Error].
 */
public sealed interface Result<out D, out E : Error> {

    /**
     * Represents a successful result of an operation.
     *
     * @param <D> the type of the data.
     * @property data the data returned by the successful operation.
     */
    public data class Success<out D>(val data: D) : Result<D, Nothing>

    /**
     * Represents a failed result of an operation.
     *
     * @param <E> the type of the error, must extend [Error].
     * @property error the error returned by the failed operation.
     */
    public data class Failure<out E : Error>(val error: E) : Result<Nothing, E>

    public companion object
}