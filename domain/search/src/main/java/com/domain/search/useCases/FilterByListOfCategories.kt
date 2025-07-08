package com.domain.search.useCases

import com.domain.search.model.Media

class FilterByListOfCategories {
    operator fun invoke(categories: List<String>, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media ->
            categories.any { category ->
                media.categories.contains(category)
            }
        }
    }
}