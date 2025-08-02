package com.domain.media.useCase

import com.domain.media.model.Media

class FilterMediaByRatingUseCase {
    operator fun invoke(rating: Float, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media ->
            media.rating?.let {
                media.rating >= rating
            } == true
        }
    }
}