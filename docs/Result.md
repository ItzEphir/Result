# Result

The `Result` interface represents the outcome of an operation, encapsulating either a success or a failure. It is a
generic, sealed interface designed to encapsulate a successful result containing data or a failure result containing an
error.

## Interface Definition

```kotlin
sealed interface Result<out D, out E : Error>
```

## Type Parameters

- **D**: The type of the data in case of a success result.
- **E**: The type of the error in case of a failure result, must extend the `Error` class.

## Nested Classes

The `Result` interface contains two nested data classes:

### Success

Represents a successful outcome of an operation.

```kotlin
data class Success<out D>(val data: D) : Result<D, Nothing>
```

**Type Parameters:**

- **D**: The type of the data.

**Properties:**

- **data** (`D`): The data returned by the successful operation.

### Failure

Represents a failed outcome of an operation.

```kotlin
data class Failure<out E : Error>(val error: E) : Result<Nothing, E>
```

**Type Parameters:**

- **E**: The type of the error, must extend the `Error` class.

**Properties:**

- **error** (`E`): The error returned by the failed operation.

## Usage Example

```kotlin
fun fetchData(): Result<String, MyError> {
    // Some operation
    return if (operationSuccessful) {
        Result.Success("data")
    } else {
        Result.Failure(MyError("An error occurred"))
    }
}
```

In this example, `fetchData` is a function that returns a `Result` object which could be either a `Result.Success`
containing a string value or a `Result.Failure` containing a custom error of type `MyError`.

