package com.domain.search.useCases

import com.domain.search.model.Media
import com.domain.search.repository.SearchMediaRepository

class GetMediaByActorName(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(actorName: String): List<Media> {
        return searchMediaRepository.getMediaByActor(actorName)
    }
}