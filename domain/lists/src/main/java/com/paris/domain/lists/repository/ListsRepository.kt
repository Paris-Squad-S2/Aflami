package com.paris.domain.lists.repository

import com.paris.domain.lists.entity.Lists

interface ListsRepository {
    suspend fun getLists(page: Int): List<Lists>
}