package com.repository.lists

import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.repository.ListsRepository
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import com.repository.lists.mapper.toDomain

class ListsRepositoryImp(
    private val listRemoteDataSource: ListsRemoteDataSource
) : ListsRepository {
    override suspend fun getLists(page: Int): List<Lists> {
        val accountId = listRemoteDataSource.getAccountId()
        return listRemoteDataSource.getLists(page = page, accountId = accountId).toDomain()
    }

    /*    private suspend fun <T> safeCall(exception: AflamiException, call: suspend () -> T): T {
            if (networkConnectionChecker.isConnected.value.not()) {
                throw NoInternetConnectionException()
            }
            return try {
                call()
            } catch (e: AflamiException) {
                throw e
            } catch (_: Exception) {
                throw exception
            }
        }*/
}