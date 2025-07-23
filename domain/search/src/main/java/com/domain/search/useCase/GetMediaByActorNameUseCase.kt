package com.domain.search.useCase

import com.domain.search.model.Media
import com.domain.search.model.MediaType
import com.domain.search.repository.SearchMediaRepository

class GetMediaByActorNameUseCase(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(actorName: String,page:Int): List<Media> {
        return searchMediaRepository.getMediaByActor(actorName = actorName, page = page)
            .filter { media -> media.type == MediaType.MOVIE }
    }
}