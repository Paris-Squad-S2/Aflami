package com.datasource.remote.mediadetails.utils

import io.ktor.client.call.body
import io.ktor.client.statement.HttpResponse
import io.ktor.http.isSuccess

suspend inline fun <reified T> safeApiCall(
    execute: () -> HttpResponse
): T {
    val response = try {
        execute()
    } catch (e: Exception) {
        throw NetworkException.UnknownException("Unknown network error: ${e.message}")
    }

    if (response.status.isSuccess()) {
        return try {
            response.body<T>()
        } catch (e: Exception) {
            throw NetworkException.UnknownException("Error decoding response: ${e.message}")
        }
    } else {
        throw NetworkErrorMapper.toNetworkException(response)
    }
}

