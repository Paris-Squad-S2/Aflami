package com.paris.domain.lists.useCase

import com.paris.domain.lists.repository.ListsRepository

class GetMovieListIdUseCase(
    private val repository: ListsRepository
) {
    suspend operator fun invoke(movieId: Int): String? {
        val lists = repository.getLists(page = 1)
        return lists.firstOrNull { list ->
            val movieList = repository.getListDetails(page = 1, listId = list.id.toString())
            movieList.items.any { it.id == movieId }
        }?.id?.toString()
    }
}