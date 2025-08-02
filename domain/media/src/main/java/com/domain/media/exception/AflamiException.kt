package com.domain.media.exception

open class AflamiException(message: String) : Exception(message)

class NoInternetConnectionException : AflamiException("Please connect your device to the internet")
class NoMediaForCountryException : AflamiException("No movies found for the given country")
class NoMediaForActorException : AflamiException("No media found for the given actor")
class NoMediaForSearchException : AflamiException("No media matched your search term")
class NoCategoriesFoundException : AflamiException("No categories found ")