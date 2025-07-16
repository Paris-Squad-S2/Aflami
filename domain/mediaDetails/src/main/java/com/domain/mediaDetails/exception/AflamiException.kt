package com.domain.mediaDetails.exception

abstract class AflamiException(message:String) : Exception(message)
open class NetworkConnectionException(message:String):AflamiException(message)
open class LocalDataSourceException(message:String):AflamiException(message)


class UnauthorizedException : NetworkConnectionException("Unauthorized access: Please check your credentials and try again")
class RequestTimeoutException : NetworkConnectionException("Request timed out: Please check your internet connection and try again")
class ServerException : NetworkConnectionException("Server error: Please try again later or contact support")
class UnknownException : NetworkConnectionException("An unknown error occurred: Please try again or contact support")
class NoMovieDetailsFoundException : LocalDataSourceException("No movie details found in the local database")