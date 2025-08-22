package com.paris.domain.media.useCase.media

import com.paris.domain.media.entity.Media
import com.paris.domain.media.entity.MediaType
import com.paris.domain.media.repository.SearchMediaRepository

class GetMediaByActorNameUseCase(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(actorName: String, page: Int): List<Media> {
        return searchMediaRepository.getMediaByActor(actorName = actorName, page = page)
            .filter { media -> media.type == MediaType.Movie }
    }
}