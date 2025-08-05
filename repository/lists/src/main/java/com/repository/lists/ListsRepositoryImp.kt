package com.repository.lists

import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.entity.Response
import com.paris.domain.lists.exception.ListsNetworkException
import com.paris.domain.lists.repository.ListsRepository
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import com.repository.lists.exeptions.NetworkException
import com.repository.lists.mapper.toDomain

class ListsRepositoryImp(
    private val listRemoteDataSource: ListsRemoteDataSource
) : ListsRepository {
    override suspend fun getLists(page: Int): List<Lists> = handleListsExceptions {
        val accountId = listRemoteDataSource.getAccountId()
        return listRemoteDataSource.getLists(page = page, accountId = accountId).toDomain()
    }

    override suspend fun getListDetails(page: Int, listId: String): ListDetails = handleListsExceptions {
        return listRemoteDataSource.getListDetails(page = page, listId = listId).toDomain()
    }

    override suspend fun deleteList(listId: String): Response = handleListsExceptions {
        return listRemoteDataSource.deleteList(listId).toDomain()
    }

    override suspend fun createList(name: String): Response = handleListsExceptions {
        return listRemoteDataSource.createList(name).toDomain()
    }

    override suspend fun addMovieToList(listId: String, movieId: Int): Response = handleListsExceptions {
        return listRemoteDataSource.addMovieToList(listId , movieId).toDomain()
    }

    override suspend fun removeMovieFromList(listId: String, movieId: Int): Response = handleListsExceptions {
        return listRemoteDataSource.removeMovieFromList(listId, movieId).toDomain()
    }


    private inline fun <T> handleListsExceptions(block: () -> T): T {
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
}