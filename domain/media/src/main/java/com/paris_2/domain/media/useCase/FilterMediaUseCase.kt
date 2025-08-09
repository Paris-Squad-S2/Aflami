package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.Genre

class FilterMediaUseCase {
    operator fun invoke(categories: List<Genre>, mediaList: List<Media>): List<Media> {
        return mediaList.filter { media ->
            categories.any { genre ->
                media.genres.contains(genre)
            }
        }
    }
}