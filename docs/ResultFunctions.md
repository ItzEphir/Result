# Result Functions

This `ResultFunctions.kt` file provides a set of extension functions designed to operate on a `Result` type. These
extensions offer various utility methods which facilitate the handling and transformation of result objects, improving
the ergonomics of working with success and error outcomes in a functional style.

## Overview of Provided Extensions

### `onSuccess`

```kotlin
inline fun <D, E : Error> Result<D, E>.onSuccess(action: (D) -> Unit): Result<D, E>
```

Executes the given action if the `Result` is a success. This allows for performing side effects on the success value
without altering the result.

### `onFailure`

```kotlin
inline fun <reified E : RootE, RootE : Error> Result<*, RootE>.onFailure(action: (E) -> Unit): Result<*, RootE>
```

Executes the given action if the `Result` is a failure containing an error of type `E`, where `E` is a subtype of
`RootE`. This allows for performing side effects on the error value without altering the result.

### `map`

```kotlin
inline fun <D, R, E : Error> Result<D, E>.map(transform: (D) -> R): Result<R, E>
```

Transforms the data of a success result using the provided transform function, producing a new result with the
transformed data. If the result is a failure, the original error is preserved.

### `flat`

```kotlin
fun <D, E : Error> Result<Result<D, E>, E>.flat(): Result<D, E>
```

Flattens a nested result structure to a single result. If the outer result is a success, the contained inner result is
returned. If the outer result is a failure, the original error is preserved.

### `flatMap`

```kotlin
inline fun <D, R, E : Error> Result<D, E>.flatMap(transform: (D) -> Result<R, E>): Result<R, E>
```

Maps a success result to a new result using the provided transform function and then flattens the result. If the
original result is a failure, the original error is preserved.

### `get`

```kotlin
fun <D, E : Error> Result<D, E>.get(): D
```

Returns the data of a success result. If the result is a failure, a `ClassCastException` is thrown.

### `getOrNull`

```kotlin
fun <D, E : Error> Result<D, E>.getOrNull(): D?
```

Returns the data of a success result, or `null` if the result is a failure.

### `getOrElse`

```kotlin
inline fun <D, E : Error> Result<D, E>.getOrElse(action: (E) -> D): D
```

Returns the data of a success result, or the result of the provided action if the result is a failure.

### `toResult`

```kotlin
fun <D, E : Error> kotlin.Result<D>.toResult(throwableMap: (Throwable) -> E): Result<D, E>
```

Converts a Kotlin `kotlin.Result` to a custom `Result` type, mapping any throwable to the custom error type using the
provided `throwableMap` function.

### `toKotlinResult` (with custom error map)

```kotlin
fun <D, E : Error> Result<D, E>.toKotlinResult(errorMap: (E) -> Throwable): kotlin.Result<D>
```

Converts a custom `Result` to a Kotlin `kotlin.Result`, mapping the custom error to a throwable using the provided
`errorMap` function.

### `toKotlinResult` (with throwable error)

```kotlin
fun <D, E : ErrorWithThrowable> Result<D, E>.toKotlinResult(): kotlin.Result<D>
```

Converts a custom `Result` with a throwable error to a Kotlin `kotlin.Result`.

## Usage Examples

### `onSuccess` Example

```kotlin
val result: Result<Int, MyError> = Result.Success(42)
result.onSuccess { println(it) }  // prints: 42
```

### `onFailure` Example

```kotlin
val result: Result<Int, MyError> = Result.Failure(MyError())
result.onFailure<MyError> { println(it) } // prints MyError.toString()
```

### `map` Example

```kotlin
val result: Result<Int, MyError> = Result.Success(42)
val mappedResult: Result<String, MyError> = result.map { it.toString() } // data is string
```

### `flat` Example

```kotlin
fun fetch(): Result<String, Error> {
    // some fetch
    return Result.Success("success")
}

fun <T> convert(block: () -> T): Result<T, Error> {
    // some converts
    return Result.Success(block())
}

fun fetchAndConvert(): Result<String, Error> {
    val nestedResult: Result<Result<String, Error>, Error> = convert { fetch() }
    val flatResult: Result<String, Error> = nestedResult.flat()
}
```

### `flatMap` Example

```kotlin
fun firstFetch(): Result<String, Error> {
    // some fetch
    return Result.Success("42")
}

fun secondFetch(value: String): Result<Int, Error> {
    // some fetch
    return Result.Success(value.toInt())
}

fun fetchBoth(): Result<Int, Error>{
    return firstFetch().flatMap { secondFetch(it) }
}
```

### `toResult` Example

```kotlin
val kotlinResult: kotlin.Result<Int> = kotlin.runCatching { 42 }
val customResult: Result<Int, MyError> = kotlinResult.toResult { MyError(it.message) }
```

### `toKotlinResult` Example

```kotlin
val customResult: Result<Int, MyError> = Result.Failure(MyError())
val kotlinResult: kotlin.Result<Int> = customResult.toKotlinResult { Throwable(it.message) }
```
