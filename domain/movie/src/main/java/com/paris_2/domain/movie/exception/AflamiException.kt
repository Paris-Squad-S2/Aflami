package com.paris_2.domain.movie.exception

abstract class AflamiException(message:String) : Exception(message)

class NetworkException(message: String) : AflamiException(message)
class NoInternetConnectionException(message: String = "No internet connection") : AflamiException(message)
class NoFundGalleryTvShowException(message: String = "no found gallery tv show"): AflamiException(message)
class NoFoundMovieException(message: String = "No found movie") : AflamiException(message)
class NoFundGalleryMovieException(message: String = "no found gallery movie"): AflamiException(message)