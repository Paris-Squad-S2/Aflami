package com.paris.domain.lists.useCase

import com.paris.domain.lists.repository.ListsRepository

class GetListDetailsUseCase(
    private val listsRepository: ListsRepository
) {
    suspend operator fun invoke(page: Int, listId: String) =
        listsRepository.getListDetails(page = page, listId = listId)
}