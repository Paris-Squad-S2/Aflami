package com.repository.guessgame.utils

import com.paris.domain.game.exception.GameException
import com.paris.domain.game.exception.NoInternetConnectionException

suspend fun <T> safeCall(
    exception: GameException,
    networkChecker: NetworkConnectionChecker? = null,
    call: suspend () -> T
): T {
    if (networkChecker?.isConnected?.value == false) {
        throw NoInternetConnectionException()
    }
    return try {
        call()
    } catch (e: GameException) {
        throw e
    } catch (_: Exception) {
        throw exception
    }
}