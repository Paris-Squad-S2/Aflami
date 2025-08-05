package com.repository.lists.dataSource.remote

import com.repository.lists.model.dto.ListDetailsDto
import com.repository.lists.model.dto.ListsDto
import com.repository.lists.model.dto.ResponseDto

interface ListsRemoteDataSource {
    suspend fun getLists(page: Int, accountId: String): ListsDto
    suspend fun getAccountId(): String
    suspend fun getListDetails(page: Int, listId: String): ListDetailsDto
    suspend fun deleteList(listId: String): ResponseDto
    suspend fun createList(name: String): ResponseDto
    suspend fun addMovieToList(listId: String , movieId: Int): ResponseDto
    suspend fun removeMovieFromList(listId: String, movieId: Int): ResponseDto
}