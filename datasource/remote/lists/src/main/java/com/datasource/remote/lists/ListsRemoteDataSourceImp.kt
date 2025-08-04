package com.datasource.remote.lists

import com.datasource.remote.lists.service.RetrofitListApiService
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import com.repository.lists.model.dto.ListsDto
import jakarta.inject.Inject

class ListsRemoteDataSourceImp @Inject constructor(
    private val listApiService: RetrofitListApiService
) : ListsRemoteDataSource {
    override suspend fun getLists(page: Int , accountId: String): ListsDto {
        return listApiService.getListDetails(accountId =accountId , page = page)
    }

    override suspend fun getAccountId(): String =
        listApiService.getAccountDetails().id.toString()

}