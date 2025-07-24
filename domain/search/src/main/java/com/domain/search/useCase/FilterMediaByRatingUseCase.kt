package com.domain.search.useCase

import com.domain.search.model.Media

class FilterMediaByRatingUseCase {
    operator fun invoke(rating: Float, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media -> media.rating >= rating }
    }
}