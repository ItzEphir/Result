# Result

### Overview

This is a custom result library that can be used in case where developer needs to process many different errors. Result
can be simply transformed, integrated with kotlin.Result

#### Docs:

* [Result class](docs/Result.md)
* [Error interface](docs/Error.md)
* [StringError](docs/StringError.md)
* [ErrorWithThrowable](docs/ErrorWithThrowable.md)
* [Result Functions](docs/ResultFunctions.md)

### Examples:

``` kotlin
fun fetch(): Result<String, Error> {
    // some fetching
    return Result.Success<String>("success")
}

fun uploadToStorage(data: Int): Result<Unit, Error> {
    // some uploading
    return Result.success(Unit)
}

fun fetchAndUploadToStorage(): Result<Unit, Error> {
    return fetch().map {
        it.toInt()
    }.flatMap {
        uploadToStorage(it)
    }
}

fun ooops(): Result<String, Error> {
    // some failed functionality
    return Result.Failure<String>("some failure")
}

fun main() {
    fetchAndUploadToStorage()
        .onSuccess {
            println("success")
        }.onFailure {
            println("error $it")
        }
        .flatMap {
            ooops()
        }
}
```


