package com.domain.home.exception

abstract class AflamiException(message:String) : Exception(message)

class NoInternetConnectionException(message: String = "No internet connection") : AflamiException(message)
class NoCategoriesFoundException: AflamiException("No categories found")
class NoPopularMediaFoundException(message: String = "No popular media found") : AflamiException(message)
class NoTopRatingMediaFoundException(message: String = "No top rating media found") : AflamiException(message)
class NoUpComingMediaFoundException(message: String = "No upcoming media found") : AflamiException(message)
class NoMediaPlayingFoundException(message: String = "No media currently playing") : AflamiException(message)
class GetContinueWatchingMediaException(message: String = "Something went wrong while fetching continue watching media") : AflamiException(message)
