package com.domain.home.exception

abstract class AflamiException(message:String) : Exception(message)

class NetworkException(message: String) : AflamiException(message)
class NoCategoriesFoundException: AflamiException("No categories found ")
