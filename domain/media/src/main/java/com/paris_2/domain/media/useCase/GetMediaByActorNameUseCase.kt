package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import com.paris_2.domain.media.repository.SearchMediaRepository
import javax.inject.Inject

class GetMediaByActorNameUseCase @Inject constructor(
    private val searchMediaRepository: SearchMediaRepository,
) {
    suspend operator fun invoke(actorName: String, page: Int): List<Media> {
        return searchMediaRepository.getMediaByActor(actorName = actorName, page = page)
            .filter { media -> media.type == MediaType.MOVIE }
    }
}