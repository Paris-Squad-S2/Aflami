package com.domain.search.useCase

import com.domain.search.model.Media

class FilterMediaUseCase {
    operator fun invoke(categories: List<Int>, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media ->
            categories.any { category ->
                media.categoryIds.contains(category)
            }
        }
    }
}