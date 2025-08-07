package com.paris.domain.lists.repository

import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.entity.Response

interface ListsRepository {
    suspend fun getLists(page: Int): List<Lists>
    suspend fun getListDetails(page: Int, listId: String): ListDetails
    suspend fun deleteList(listId: String): Response
    suspend fun createList(name: String): Response
    suspend fun addMovieToList(listId: String, movieId: Int): Response
    suspend fun removeMovieFromList(listId: String, movieId: Int): Response
}