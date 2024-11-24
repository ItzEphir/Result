package io.github.itzephir.result

/**
 * Represents an error with an associated {@link Throwable}.
 */
public interface ErrorWithThrowable : Error {
    public val throwable: Throwable
}