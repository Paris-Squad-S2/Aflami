package com.repository.search.exception

abstract class AflamiException(message:String) : Exception(message)
open class NetworkConnectionException(message:String):AflamiException(message)
open class LocalDataSourceException(message:String):AflamiException(message)


class NoInternetConnectionException():NetworkConnectionException("Please connect your device to the internet")
class NoDataForCountryException():LocalDataSourceException("No movies found for the given country")
class NoDataForActorException():LocalDataSourceException("No media found for the given actor")
class NoDataWereFoundException():LocalDataSourceException("No media matched your search term")