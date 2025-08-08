package com.paris.domain.lists.useCase

import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.repository.ListsRepository

class GetListUseCase(
private val listsRepository: ListsRepository
) {
    suspend operator fun invoke(page: Int):List<Lists> {
        return listsRepository.getLists(page)
    }
}