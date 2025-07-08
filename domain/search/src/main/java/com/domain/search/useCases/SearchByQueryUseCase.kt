package com.domain.search.useCases

import com.domain.search.model.Media

class SearchByQueryUseCase(
//     private val searchMediaRepository: SearchMediaRepository
) {

    suspend operator fun invoke(query: String): List<Media> {
        return TemporaryFakeData.mediaList
//        return searchMediaRepository.getMediaByQuery(query)
    }
}
