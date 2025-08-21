package com.repository.media.util

import com.paris.domain.media.exception.AflamiException
import com.paris.domain.media.exception.NoInternetConnectionException

suspend fun <T> safeCall(
    exception: AflamiException,
    networkConnectionChecker: NetworkConnectionChecker,
    call: suspend () -> T
): T {
    if (!networkConnectionChecker.isConnected.value) {
        throw NoInternetConnectionException()
    }
    return try {
        call()
    } catch (e: AflamiException) {
        throw e
    } catch (_: Exception) {
        throw exception
    }
}
