package com.domain.media.useCase

import com.domain.media.model.Media
import com.domain.media.model.MediaType
import com.domain.media.repository.SearchMediaRepository

class GetMediaByActorNameUseCase(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(actorName: String, page: Int): List<Media> {
        return searchMediaRepository.getMediaByActor(actorName = actorName, page = page)
            .filter { media -> media.type == MediaType.MOVIE }
    }
}