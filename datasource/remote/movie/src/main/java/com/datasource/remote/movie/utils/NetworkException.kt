package com.datasource.remote.movie.utils

import io.ktor.utils.io.errors.IOException

sealed class NetworkException(message: String) : IOException(message) {
    class UnauthorizedException(message: String) : NetworkException(message)
    class RequestTimeoutException(message: String) : NetworkException(message)
    class ServerException(message: String) : NetworkException(message)
    class UnknownException(message: String) : NetworkException(message)
}


