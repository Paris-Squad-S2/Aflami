package com.paris.domain.media.useCase

import com.paris.domain.media.entity.Category
import com.paris.domain.media.entity.Media

class FilterMediaUseCase {
    operator fun invoke(categories: List<Category>, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media ->
            categories.any { category ->
                media.categories.contains(category)
            }
        }
    }
}