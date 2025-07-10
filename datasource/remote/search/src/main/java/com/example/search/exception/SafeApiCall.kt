package com.example.search.exception

import android.util.Log

private const val TAG = "SafeApiCall"

suspend fun <T> safeApiCall(
    errorMessage: String,
    apiCall: suspend () -> T
): T {
    return try {
        apiCall()
    } catch (e: Exception) {
        Log.e(TAG, errorMessage, e)
        throw SearchNetworkException(errorMessage, e)
    }
}
