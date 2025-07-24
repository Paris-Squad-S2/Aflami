package com.domain.home.exception

abstract class AflamiException(message:String) : Exception(message)
class NoCategoriesFoundException(message: String = "No categories found "): AflamiException(message)
class NoPopularMediaFoundException(message: String = "No popular media found") : AflamiException(message)

class NoTopRatingMediaFoundException(message: String = "No top rating media found") : AflamiException(message)

class NoUpComingMediaFoundException(message: String = "No UpComing media found") : AflamiException(message)

class NoNowPlayingMediaFoundException(message: String = "No NowPlaying media found") : AflamiException(message)



