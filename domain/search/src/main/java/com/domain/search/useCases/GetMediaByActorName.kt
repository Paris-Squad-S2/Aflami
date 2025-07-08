package com.domain.search.useCases

import com.domain.search.model.Media

class GetMediaByActorName(
//     private val searchMediaRepository: SearchMediaRepository
){

    suspend operator fun invoke(actorName: String): List<Media> {
        return TemporaryFakeData.mediaList
//        return searchMediaRepository.getMediaByActor(actorName)
    }
}