package com.example.search.exception

suspend fun <T> safeApiCall(
    errorMessage: String,
    apiCall: suspend () -> T
): T {
    return try {
        apiCall()
    } catch (e: Exception) {
        throw SearchNetworkException(errorMessage, e)
    }
}
