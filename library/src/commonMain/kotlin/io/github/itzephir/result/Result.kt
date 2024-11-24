package io.github.itzephir.result

/**
 * A sealed interface representing the result of an operation, which can be either a success or a failure.
 *
 * @param <D> the type of the data in case of success.
 * @param <E> the type of the error in case of failure, must extend [Error].
 */
public sealed interface Result<out D, out E: Error> {

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
}