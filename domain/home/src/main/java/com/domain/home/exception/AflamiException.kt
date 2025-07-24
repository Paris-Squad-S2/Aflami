package com.domain.home.exception

abstract class AflamiException(message:String) : Exception(message)

class NoInternetConnectionException(message: String = "No internet connection") : AflamiException(message)
class NoCategoriesFoundException: AflamiException("No categories found ")
class NoPopularMediaFoundException(message: String = "no found popular media") : AflamiException(message)
class NoTopRatingMediaFoundException(message: String = "no found top rating media") : AflamiException(message)
class NoUpComingMediaFoundException(message: String = "no found up Coming media") : AflamiException(message)
class NoMediaPlayingFoundException(message: String = "no found media playing") : AflamiException(message)
class addMediaToLocalException(message: String = "something wrong when add media") : AflamiException(message)
class catchMediaFromLocalException(message: String = "something wrong when catch media") : AflamiException(message)
/*
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
*/