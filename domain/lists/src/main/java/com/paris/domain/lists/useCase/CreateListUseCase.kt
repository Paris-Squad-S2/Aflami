package com.paris.domain.lists.useCase

import com.paris.domain.lists.entity.Response
import com.paris.domain.lists.repository.ListsRepository


class CreateListUseCase(private val repository: ListsRepository) {
    suspend operator fun invoke(name: String): Response {
        return repository.createList(name)
    }
}
