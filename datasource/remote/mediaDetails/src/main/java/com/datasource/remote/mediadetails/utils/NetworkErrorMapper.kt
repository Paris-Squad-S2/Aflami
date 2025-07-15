package com.datasource.remote.mediadetails.utils

import io.ktor.client.statement.HttpResponse

object NetworkErrorMapper {
    fun toNetworkException(response: HttpResponse): NetworkException {
        return when (response.status.value) {
            401 -> NetworkException.UnauthorizedException("Unauthorized")
            408 -> NetworkException.RequestTimeoutException("Request Timeout")
            in 500..599 -> NetworkException.ServerException("Server Error")
            else -> NetworkException.UnknownException("Unknown network error with status: ${response.status.value}")
        }
    }
}


