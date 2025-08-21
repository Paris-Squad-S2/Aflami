package com.paris.domain.lists.useCase

import com.paris.domain.lists.repository.ListsRepository


class RemoveMovieFromListUseCase(private val repository: ListsRepository) {
    suspend operator fun invoke(listId: String, movieId: Int): Boolean {
        return repository.removeMovieFromList(listId, movieId)
    }
}
