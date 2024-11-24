package com.github.itzephir.result

import kotlin.test.Test
import kotlin.test.assertEquals

class ResultTest {

    @Test
    fun `test success result`() {
        val expected = "success"
        val actual = (successString as Result.Success).data
        assertEquals(expected, actual)
    }

    @Test
    fun `test failure result`() {
        val expected = exception
        val actual = ((failureString as Result.Failure).error as TestErrors.TestError).throwable
        assertEquals(expected, actual)
    }


    companion object {
        private val exception = Exception()
        private val successString: Result<String, TestErrors> =
            Result<String, TestErrors>.Success<String>("success")
        private val failureString: Result<String, TestErrors> =
            Result<String, TestErrors>.Failure<TestErrors>(TestErrors.TestError(exception))
    }
}