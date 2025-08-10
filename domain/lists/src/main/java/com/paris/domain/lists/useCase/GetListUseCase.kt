package com.paris.domain.lists.useCase

import com.paris.domain.lists.entity.Lists
import com.paris.domain.lists.repository.ListsRepository
import javax.inject.Inject

class GetListUseCase @Inject constructor(
    private val listsRepository: ListsRepository
) {
    suspend operator fun invoke(page: Int): List<Lists> {
        return listsRepository.getLists(page)
    }
}