package com.domain.media.useCase

import com.domain.media.model.Media

class FilterMediaUseCase {
    operator fun invoke(categories: List<Int>, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media ->
            categories.any { category ->
                media.categoryIds.contains(category)
            }
        }
    }
}