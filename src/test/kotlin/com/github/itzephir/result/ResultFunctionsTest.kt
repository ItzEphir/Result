package com.github.itzephir.result

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

class ResultFunctionsTest {
    @Test
    fun `test onSuccess`() {
        failureString.onSuccess {
            return assert(false) { it }
        }
        successString.onSuccess {
            return assert(true) { it }
        }
        assert(false)
    }

    @Test
    fun `test onFailure`() {
        successString.onFailure {
            return assert(false) { it }
        }
        failureString.onFailure {
            return assert(true) { it }
        }
        assert(false)
    }

    @Test
    fun `test onFailure with TestError`() {
        anotherFailureString.onFailure<TestErrors.TestError, TestErrors> {
            return assert(false) { it }
        }
        failureString.onFailure<TestErrors.TestError, TestErrors> {
            return assert(true) { it }
        }
        return assert(false)
    }

    @Test
    fun `test successful map`() {
        val expected = Result.Success(42)
        val actual = Result<String, TestErrors>.Success<String>("42").map { it.toInt() }
        assertEquals(expected, actual)
    }

    @Test
    fun `test failed map`() {
        val expected = Result.Failure<TestErrors>(TestErrors.AnotherTestError)
        val actual = anotherFailureString.map { it.toInt() }
        assertEquals(expected, actual)
    }

    @Test
    fun `test successful flat`() {
        val expected = successString
        val actual = Result.Success(successString).flat()
        assertEquals(expected, actual)
    }

    @Test
    fun `test inner failed flat`() {
        val expected = failureString
        val actual = Result.Success(failureString).flat()
        assertEquals(expected, actual)
    }

    @Test
    fun `test outer failed flat`() {
        val expected: Result<String, TestErrors> = failureString
        val actual: Result<String, TestErrors> =
            Result<Result<String, TestErrors>, TestErrors>.Failure<TestErrors>(TestErrors.TestError(exception)).flat()
        assertEquals(expected, actual)
    }

    @Test
    fun `test successful flat map`() {
        val expected = Result.Success(42)
        val actual = Result.Success("42").flatMap { Result.Success(it.toInt()) }
        assertEquals(expected, actual)
    }

    @Test
    fun `test inner failed flat map`() {
        val expected = anotherFailureString
        val actual = Result.Success("42").flatMap { Result.Failure<TestErrors>(TestErrors.AnotherTestError) }
        assertEquals(expected, actual)
    }

    @Test
    fun `test outer failed flat map`() {
        val expected: Result<Int, TestErrors> = Result.Failure<TestErrors>(TestErrors.TestError(exception))
        val actual = failureString.flatMap { Result.Success(it.toInt()) }
        assertEquals(expected, actual)
    }

    @Test
    fun `test successful get`() {
        val expected = "success"
        val actual = successString.get()
        assertEquals(expected, actual)
    }

    @Test
    fun `test failed get`() {
        assertFails {
            anotherFailureString.get()
        }
    }

    @Test
    fun `test successful getOrNull`() {
        val expected = "success"
        val actual = successString.getOrNull()
        assertEquals(expected, actual)
    }

    @Test
    fun `test null in successful getOrNull`() {
        val expected = null
        val actual = Result<String?, TestErrors>.Success(null).getOrNull()
        assertEquals(expected, actual)
    }

    @Test
    fun `test failed getOrNull`() {
        val expected = null
        val actual = failureString.getOrNull()
        assertEquals(expected, actual)
    }

    @Test
    fun `test successful getOrElse`() {
        successString.getOrElse {
            return assert(false)
        }
        assert(true)
    }

    @Test
    fun `test failed getOrElse`() {
        failureString.getOrElse {
            return assert(true)
        }
        assert(false)
    }

    @Test
    fun `test returns successful getOrElse`() {
        val expected = "success"
        val actual = successString.getOrElse { "wrong" }
        assertEquals(expected, actual)
    }

    @Test
    fun `test returns failed getOrElse`() {
        val expected = "wrong"
        val actual = failureString.getOrElse { "wrong" }
        assertEquals(expected, actual)
    }

    @Test
    fun `test successful toResult`() {
        val expected = successString
        val actual = kotlin.Result.success("success").toResult { TestErrors.TestError(exception) }
        assertEquals(expected, actual)
    }

    @Test
    fun `test failed toResult`() {
        val expected = failureString
        val actual = kotlin.Result.failure<String>(exception).toResult { TestErrors.TestError(exception) }
        assertEquals(expected, actual)
    }

    @Test
    fun `test successful toKotlinResult`() {
        val expected = kotlin.Result.success("success")
        val actual = successString.toKotlinResult { exception }
        assertEquals(expected, actual)
    }

    @Test
    fun `test failed toKotlinResult`(){
        val expected = kotlin.Result.failure<String>(exception)
        val actual = failureString.toKotlinResult { exception }
        assertEquals(expected, actual)
    }

    @Test
    fun `test successful toKotlinResult for ErrorWithThrowable`(){
        val expected = kotlin.Result.success("success")
        val actual = successStringWithThrowable.toKotlinResult()
        assertEquals(expected, actual)
    }

    @Test
    fun `test failed toKotlinResult for ErrorWithThrowable`(){
        val expected = kotlin.Result.failure<String>(exception)
        val actual = failureStringWithThrowable.toKotlinResult()
        assertEquals(expected, actual)
    }

    companion object {
        private val exception = Exception()
        private val successString: Result<String, TestErrors> =
            Result<String, TestErrors>.Success<String>("success")
        private val failureString: Result<String, TestErrors> =
            Result<String, TestErrors>.Failure<TestErrors>(TestErrors.TestError(exception))
        private val anotherFailureString: Result<String, TestErrors> =
            Result<String, TestErrors>.Failure<TestErrors>(TestErrors.AnotherTestError)

        private val successStringWithThrowable: Result<String, TestErrors.TestError> =
            Result<String, TestErrors.TestError>.Success<String>("success")
        private val failureStringWithThrowable: Result<String, TestErrors.TestError> =
            Result<String, TestErrors.TestError>.Failure<TestErrors.TestError>(TestErrors.TestError(exception))
    }
}