package com.domain.search.useCases

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository

class GetMediaByActorNameUseCase(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(actorName: String): List<Media> {
        return searchMediaRepository.getMediaByActor(actorName)
            .filter { media -> media.type == MediaType.MOVIE }
    }
}