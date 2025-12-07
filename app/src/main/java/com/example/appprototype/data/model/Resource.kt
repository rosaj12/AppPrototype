package com.example.appprototype.data.model

/**
 * Sealed class to represent different states of a data operation
 * Used to handle loading, success, and error states in the UI
 */
sealed class Resource<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}
