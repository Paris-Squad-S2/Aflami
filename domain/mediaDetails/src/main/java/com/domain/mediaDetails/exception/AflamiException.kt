package com.domain.mediaDetails.exception

abstract class AflamiException(message:String) : Exception(message)

class NetworkException(message: String) : AflamiException(message)
class NoInternetConnectionException(message: String = "No internet connection") : AflamiException(message)