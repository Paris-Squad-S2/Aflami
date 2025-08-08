package com.paris.domain.lists.useCase

import com.paris.domain.lists.entity.ListDetails
import com.paris.domain.lists.repository.ListsRepository

class GetListDetailsUseCase(
    private val listsRepository: ListsRepository
) {
    suspend operator fun invoke(page: Int, listId: String):ListDetails {
     return   listsRepository.getListDetails(page = page, listId = listId)
    }
}