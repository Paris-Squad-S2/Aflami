package com.paris_2.datasource.remote.authentication

sealed class NetworkException(message: String? = null) : Exception(message) {
    class UnauthenticatedException(message: String? = null) : NetworkException(message)
    class ServerException(message: String? = null) : NetworkException(message)
    class UnknownException(message: String? = null) : NetworkException(message)
}