package com.datasource.remote.lists

import com.datasource.remote.lists.service.ListApiService
import com.repository.lists.dataSource.remote.ListsRemoteDataSource
import com.repository.lists.exeptions.NetworkException
import com.repository.lists.model.dto.ListDetailsDto
import com.repository.lists.model.dto.ListsDto
import com.repository.lists.model.dto.ResponseDto
import jakarta.inject.Inject
import retrofit2.HttpException

class ListsRemoteDataSourceImp @Inject constructor(
    private val listApiService: ListApiService
) : ListsRemoteDataSource {
    override suspend fun getLists(page: Int, accountId: Int): ListsDto = safeApiCall {
        listApiService.getLists(accountId = accountId, page = page)
    }

    override suspend fun getListDetails(page: Int, listId: String): ListDetailsDto = safeApiCall {
        listApiService.getListDetails(page = page, listId = listId)
    }

    override suspend fun deleteList(listId: String): ResponseDto = safeApiCall {
        listApiService.deleteList(listId)
    }

    override suspend fun createList(name: String): ResponseDto = safeApiCall {
        val requestBody = mapOf(
            "name" to name,
            "description" to "Created from Aflami app",
            "language" to "en"
        )
        listApiService.createList(requestBody)
    }

    override suspend fun addMovieToList(listId: String,movieId: Int): ResponseDto = safeApiCall {
        val requestBody = mapOf("media_id" to movieId)

        listApiService.addMovieToList(listId , requestBody)
    }

    override suspend fun removeMovieFromList(listId: String, movieId: Int): ResponseDto = safeApiCall {
        val requestBody = mapOf("media_id" to movieId)
        listApiService.removeMovieFromList(listId, requestBody)
    }

    private suspend fun <T> safeApiCall(apiCall: suspend () -> T): T {
        return try {
            apiCall()
        } catch (exception: HttpException) {
            when (exception.code()) {
                in 500..599 -> throw NetworkException.ServerException("Server error: ${exception.message()}")
                else -> throw NetworkException.UnknownException("HTTP error: ${exception.message()}")
            }
        } catch (e: Exception) {
            throw NetworkException.UnknownException("Unexpected error: ${e.message}")
        }
    }

}