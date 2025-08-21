package com.paris.domain.media.exception

open class AflamiException(message: String) : Exception(message)

class NoInternetConnectionException() :
    AflamiException("No internet connection")

class FailedException(message: String) : AflamiException(message)