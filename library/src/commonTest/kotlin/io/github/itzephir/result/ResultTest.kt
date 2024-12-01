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

package io.github.itzephir.result

import io.github.itzephir.result.models.Result
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