# Error

The `Error` interface represents an error. It is designed to provide a standard way to handle errors across different parts of the application.

## Interface Definition

```kotlin
interface Error
```

## Description

The `Error` interface is a marker interface used to encapsulate error information. Implementations of this interface should provide specific details about various types of errors.

## Usage

To use this interface, create custom error types by implementing the `Error` interface. Each custom error type can provide additional fields and methods that describe the specific error condition.

### Example

Here is a simple example of how to create a custom error type:

```kotlin
/**
 * Represents a custom error.
 */
data class CustomError(
    val message: String,
    val code: Int,
) : Error
```
