package com.paris.domain.user.exception

sealed class AuthException(message: String) : Exception(message)
class AuthNetworkException(message: String = "Network error") : AuthException(message)
class InvalidCredentialsException(message: String = "Invalid credentials") : AuthException(message)
class UnknownAuthException(message: String = "Unknown authentication error") : AuthException(message)