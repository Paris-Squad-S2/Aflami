package com.paris.domain.lists.repository

import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.entity.Lists

interface ListsRepository {
    suspend fun getLists(page: Int): List<Lists>
    suspend fun getListDetails(page: Int, listId: String): ListDetails
    suspend fun deleteList(listId: String): Boolean
    suspend fun createList(name: String): Boolean
    suspend fun addMovieToList(listId: String, movieId: Int): Boolean
    suspend fun removeMovieFromList(listId: String, movieId: Int): Boolean
}