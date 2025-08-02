package com.domain.media.useCase

import com.domain.media.model.Media
import com.domain.media.repository.SearchMediaRepository

class SearchByQueryUseCase(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(query: String, page: Int): List<Media> {
        return searchMediaRepository.getMediaByQuery(query, page)
    }
}
