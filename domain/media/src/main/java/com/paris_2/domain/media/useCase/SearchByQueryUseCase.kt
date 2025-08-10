package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.repository.SearchMediaRepository
import javax.inject.Inject

class SearchByQueryUseCase @Inject constructor(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(query: String, page: Int): List<Media> {
        return searchMediaRepository.getMediaByQuery(query, page)
    }
}
