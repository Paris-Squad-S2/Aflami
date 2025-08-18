package com.paris_2.domain.game.exception

open class GameException(message: String) : Exception(message)

class NoInternetConnectionException() :
    GameException("No internet connection")

class FailedException(message: String) : GameException(message)