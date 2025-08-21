package com.repository.lists.util

import com.paris.domain.lists.exception.ListsNetworkException
import com.repository.lists.exeptions.NetworkException

inline fun <T> handleListsExceptions(block: () -> T): T {
    try {
        return block()
    } catch (e: NetworkException.ServerException) {
        throw ListsNetworkException(e.message ?: "Server error")
    } catch (e: NetworkException.UnknownException) {
        throw ListsNetworkException(e.message ?: "Unknown authentication error")
    } catch (e: Exception) {
        throw ListsNetworkException(e.message ?: "Unknown authentication error")
    }
}