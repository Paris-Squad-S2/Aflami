package com.paris.domain.lists.useCase

import com.paris.domain.lists.repository.ListsRepository

class DeleteListUseCase(private val repository: ListsRepository) {
    suspend operator fun invoke(listId: String): Boolean {
        return repository.deleteList(listId)
    }
}
