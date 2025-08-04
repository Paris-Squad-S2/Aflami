package com.datasource.remote.lists.service

import com.repository.lists.model.dto.AccountDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import com.repository.lists.model.dto.ListsDto

interface RetrofitListApiService {

    @GET("account")
    suspend fun getAccountDetails(): AccountDto


    @GET("account/{account_id}/lists")
    suspend fun getListDetails(
        @Path("account_id") accountId: String,
        @Query("page") page: Int? = null
    ): ListsDto
}