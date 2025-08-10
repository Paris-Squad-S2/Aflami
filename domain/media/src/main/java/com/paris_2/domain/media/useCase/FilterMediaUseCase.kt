package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Category
import com.paris_2.domain.media.entity.Media
import javax.inject.Inject

class FilterMediaUseCase @Inject constructor() {
    operator fun invoke(categories: List<Category>, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media ->
            categories.any { category ->
                media.categories.contains(category)
            }
        }
    }
}