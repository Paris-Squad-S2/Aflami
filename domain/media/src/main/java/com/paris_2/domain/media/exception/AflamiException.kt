package com.paris_2.domain.media.exception

open class AflamiException(message: String) : Exception(message)

class NoInternetConnectionException(message: String = "No internet connection") : AflamiException(message)
class NoMediaForCountryException : AflamiException("No movies found for the given country")
class NoMediaForActorException : AflamiException("No media found for the given actor")
class NoMediaForSearchException : AflamiException("No media matched your search term")
class NoCategoriesFoundException : AflamiException("No categories found ")

class NoTvShowFoundException(message: String = "No found tv show") : AflamiException(message)
class NoGalleryFoundException(message: String = "no found gallery ") :
    AflamiException(message)
class NoCastFoundException(message: String = "no found Cast") : AflamiException(message)
class NoVideoFoundException(message: String = "no found video") : AflamiException(message)
class NoSeasonFoundException(message: String = "no found season tv show") : AflamiException(message)
class NoMovieFoundException(message: String = "No found movie") : AflamiException(message)
class NoReviewFoundException(message: String = "No found review") : AflamiException(message)
class NoSimilarFoundException(message: String = "No found similar") : AflamiException(message)
class NoProductionCompanyFoundException(message: String = "No found production company") :
    AflamiException(message)
class FailedToAddRatingException(message: String = "Failed to add rating") :
    AflamiException(message)

class PopularMediaException(message: String = "No popular media found") : AflamiException(message)
class TopRatingMediaException(message: String = "No top rating media found") : AflamiException(message)
class UpComingMediaException(message: String = "No upcoming media found") : AflamiException(message)
class MediaPlayingException(message: String = "No media currently playing") : AflamiException(message)
class AddMediaToContinueWatchingException(message: String = "Can't add  media to Continue Watching List") : AflamiException(message)
class GetContinueWatchingMediaException(message: String = "Something went wrong while fetching continue watching media") : AflamiException(message)
