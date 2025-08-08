package com.paris.domain.lists.useCase

import com.paris.domain.lists.entity.Response
import com.paris.domain.lists.repository.ListsRepository

class AddMovieToListUseCase(private val repository: ListsRepository) {
    suspend operator fun invoke(listId: String , movieId: Int): Response {
        return repository.addMovieToList(listId , movieId)
    }
}
