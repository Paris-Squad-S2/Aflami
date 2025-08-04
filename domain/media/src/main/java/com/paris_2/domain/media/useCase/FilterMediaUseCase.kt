package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media

class FilterMediaUseCase {
    operator fun invoke(categories: List<Int>, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media ->
            categories.any { category ->
                media.categoryIds.contains(category)
            }
        }
    }
}