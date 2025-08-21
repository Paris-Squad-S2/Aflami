package com.repository.lists

import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.repository.ListsRepository
import com.paris.repository.user.dataSource.remote.UserRemoteDataSource
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import com.repository.lists.mapper.toDomain
import com.repository.lists.util.handleListsExceptions

class ListsRepositoryImpl(
    private val listRemoteDataSource: ListsRemoteDataSource,
    private val remoteDataSource: UserRemoteDataSource,
    ) : ListsRepository {
    override suspend fun getLists(page: Int): List<Lists> = handleListsExceptions {
        val accountId = remoteDataSource.getAccountDetails().id ?: 0
        return listRemoteDataSource.getLists(page = page, accountId = accountId).toDomain()
    }

    override suspend fun getListDetails(page: Int, listId: String): ListDetails = handleListsExceptions {
         listRemoteDataSource.getListDetails(page = page, listId = listId).toDomain()
    }

    override suspend fun deleteList(listId: String) = handleListsExceptions {
         listRemoteDataSource.deleteList(listId).success?:false
    }

    override suspend fun createList(name: String) = handleListsExceptions {
         listRemoteDataSource.createList(name).success?:false
    }

    override suspend fun addMovieToList(listId: String, movieId: Int) = handleListsExceptions {
         listRemoteDataSource.addMovieToList(listId , movieId).success?:false
    }

    override suspend fun removeMovieFromList(listId: String, movieId: Int) = handleListsExceptions {
         listRemoteDataSource.removeMovieFromList(listId, movieId).success?:false
    }
}