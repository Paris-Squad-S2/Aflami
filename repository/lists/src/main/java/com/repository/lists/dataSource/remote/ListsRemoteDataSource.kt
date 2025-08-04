package com.repository.lists.dataSource.remote

import com.repository.lists.model.dto.ListsDto

interface ListsRemoteDataSource {
    suspend fun getLists(page: Int, accountId: String): ListsDto
    suspend fun getAccountId(): String
}