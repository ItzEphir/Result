# ErrorWithThrowable Interface Documentation

The `ErrorWithThrowable` interface represents an error that includes an associated `Throwable` in your Kotlin project.
It extends the base `Error` interface, adding an additional property for handling exceptions effectively.

## Interface Definition

```kotlin
interface ErrorWithThrowable : Error {
    val throwable: Throwable
}
```

## Properties

### `throwable`

- **Type:** `Throwable`
- **Description:** This property holds the `Throwable` instance associated with the error, providing additional context
  or detailed information about the root cause of the error.

## Usage

The `ErrorWithThrowable` interface can be used to represent and handle errors that need to carry detailed exception
information. By implementing this interface, you can ensure that all necessary details of an error, including the stack
trace and causation chain, are preserved and accessible.

### Example

Here's a simple usage example:

```kotlin
data class CustomError(override val throwable: Throwable) : ErrorWithThrowable

fun main() {
    try {
        // Some code that throws an exception
    } catch (e: Exception) {
        e.printStackTrace()
        val error: ErrorWithThrowable = CustomError(e)
        println("Error occurred: $error")
    }
}
```

In the above example, `CustomError` implements the `ErrorWithThrowable` interface, allowing it to carry a `Throwable`
instance and provide a meaningful error message.
