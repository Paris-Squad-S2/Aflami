package com.domain.mediaDetails.exception

abstract class AflamiException(message: String) : Exception(message)

class NoInternetConnectionException(message: String = "No internet connection") :
    AflamiException(message)
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
class FaildToAddRatingException(message: String = "Failed to add rating") :
    AflamiException(message)
