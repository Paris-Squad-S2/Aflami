package com.paris.domain.media.useCase

import com.paris.domain.media.entity.Media
import com.paris.domain.media.repository.SearchMediaRepository

class SearchByQueryUseCase(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(query: String, page: Int): List<Media> {
        return searchMediaRepository.getMediaByQuery(query, page)
    }
}
