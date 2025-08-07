package com.repository.lists.exeptions


sealed class NetworkException(message: String? = null) : Exception(message) {
    class ServerException(message: String? = null) : NetworkException(message)
    class UnknownException(message: String? = null) : NetworkException(message)
}
