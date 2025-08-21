package com.paris.domain.media.useCase

import com.paris.domain.media.entity.Media

class FilterMediaByRatingUseCase {
    operator fun invoke(rating: Float, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media ->
            media.rating?.let {
                media.rating >= rating
            } == true
        }
    }
}