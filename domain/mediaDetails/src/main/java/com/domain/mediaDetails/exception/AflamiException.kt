package com.domain.mediaDetails.exception

abstract class AflamiException(message:String) : Exception(message)

class NetworkException(message: String) : AflamiException(message)
class NoInternetConnectionException(message: String = "No internet connection") : AflamiException(message)
class NoFoundTvShowException(message: String = "No found tv show") : AflamiException(message)
class NoFundGalleryTvShowException(message: String = "no found gallery tv show"): AflamiException(message)
class NoSeasonFoundException(message: String = "no found season tv show"): AflamiException(message)