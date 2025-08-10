package com.paris.domain.lists.useCase

import com.paris.domain.lists.entity.Response
import com.paris.domain.lists.repository.ListsRepository
import javax.inject.Inject

class AddMovieToListUseCase @Inject constructor(
    private val repository: ListsRepository
) {
    suspend operator fun invoke(listId: String, movieId: Int): Response {
        return repository.addMovieToList(listId, movieId)
    }
}
