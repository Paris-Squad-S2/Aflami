package com.paris.domain.lists.useCase

import com.paris.domain.lists.repository.ListsRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope

class GetMovieListIdUseCase(
    private val repository: ListsRepository
) {
    suspend operator fun invoke(movieId: Int): String? = coroutineScope {
        val lists = repository.getLists(page = 1)
        val results = lists.map { list ->
            async {
                val movieList = repository.getListDetails(page = 1, listId = list.id.toString())
                if (movieList.items.any { it.id == movieId }) list.id else null
            }
        }.awaitAll()

        results.firstOrNull()?.toString()
    }
}