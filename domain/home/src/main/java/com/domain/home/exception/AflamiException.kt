package com.domain.home.exception

abstract class AflamiException(message:String) : Exception(message)

class NoInternetConnectionException(message: String = "No internet connection") : AflamiException(message)
class NoCategoriesFoundException: AflamiException("No categories found")
class PopularMediaException(message: String = "No popular media found") : AflamiException(message)
class TopRatingMediaException(message: String = "No top rating media found") : AflamiException(message)
class UpComingMediaException(message: String = "No upcoming media found") : AflamiException(message)
class MediaPlayingException(message: String = "No media currently playing") : AflamiException(message)
class AddMediaToContinueWatchingException(message: String = "Can't add  media to Continue Watching List") : AflamiException(message)
class GetContinueWatchingMediaException(message: String = "Something went wrong while fetching continue watching media") : AflamiException(message)
