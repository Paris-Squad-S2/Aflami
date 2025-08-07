package com.datasource.remote.lists.service

import com.repository.lists.model.dto.AccountDto
import com.repository.lists.model.dto.ListDetailsDto
import com.repository.lists.model.dto.ListsDto
import com.repository.lists.model.dto.ResponseDto
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitListApiService {

    @GET("account")
    suspend fun getAccountDetails(): AccountDto

    @GET("account/{account_id}/lists")
    suspend fun getLists(
        @Path("account_id") accountId: String,
        @Query("page") page: Int? = null
    ): ListsDto

    @GET("list/{list_id}")
    suspend fun getListDetails(
        @Path("list_id") listId: String,
        @Query("page") page: Int? = null
    ): ListDetailsDto

    @DELETE("list/{list_id}")
    suspend fun deleteList(
        @Path("list_id") listId: String,
    ): ResponseDto

    @POST("list")
    suspend fun createList(
        @Body createListRequest: Map<String, String>
    ): ResponseDto

    @POST("list/{list_id}/add_item")
    suspend fun addMovieToList(
        @Path("list_id") listId: String,
        @Body removeMovieRequest: Map<String, Int>
    ): ResponseDto

    @POST("list/{list_id}/remove_item")
    suspend fun removeMovieFromList(
        @Path("list_id") listId: String,
        @Body removeMovieRequest: Map<String, Int>
    ): ResponseDto
}