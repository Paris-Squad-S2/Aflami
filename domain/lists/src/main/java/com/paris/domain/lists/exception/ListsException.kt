package com.paris.domain.lists.exception

sealed class ListsException(message: String) : Exception(message)
class ListsNetworkException(message: String = "Network error") : ListsException(message)
