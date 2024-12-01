package io.github.itzephir.result

import io.github.itzephir.result.models.DefaultStringError
import io.github.itzephir.result.models.Error
import io.github.itzephir.result.models.StringError
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

data class CustomStringError(override val message: String) : StringError

class StringErrorTest {
    @Test
    fun `test equals StringError`() {
        val error = CustomStringError("test")
        val anotherError = CustomStringError("test")
        assertEquals(anotherError, error)
    }

    @Test
    fun `test not equals StringError`() {
        val error = TestErrors.TestError(Exception())
        val anotherError = CustomStringError("Fail")
        assertNotEquals<Error>(error, anotherError)
    }

    @Test
    fun `test not equals different StringErrors`() {
        val error: StringError = CustomStringError("Fail")
        val anotherError: StringError = DefaultStringError("Fail")
        assertNotEquals(error, anotherError)
    }
}