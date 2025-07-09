package com.domain.search.useCases

import com.domain.search.model.Media
import com.domain.search.repository.SearchMediaRepository

class SearchByQueryUseCase(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(query: String): List<Media> {
        return searchMediaRepository.getMediaByQuery(query)
    }
}
