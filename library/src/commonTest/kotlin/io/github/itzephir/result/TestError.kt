package io.github.itzephir.result

sealed interface TestErrors : Error {

    data class TestError(override val throwable: Throwable) : ErrorWithThrowable, TestErrors

    data object AnotherTestError : TestErrors
}