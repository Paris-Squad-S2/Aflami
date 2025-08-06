package com.paris_2.domain.media.useCase

import com.paris_2.domain.media.entity.Media
import com.paris_2.domain.media.entity.MediaType
import kotlinx.datetime.LocalDate

object FilterRatedMediaUseCase {
     operator fun invoke(mediaType: MediaType): List<Media> {
        return listOf(
            Media(
                id = 1,
                imageUri = "https://image.tmdb.org/t/p/w500/sample_movie_1.jpg",
                title = "The Dark Knight",
                type = MediaType.MOVIE,
                categoryIds = listOf(28, 80),
                yearOfRelease = LocalDate(2008, 7, 18),
                rating = 9.0
            ),
            Media(
                id = 2,
                imageUri = "https://image.tmdb.org/t/p/w500/sample_movie_2.jpg",
                title = "Breaking Bad",
                type = MediaType.TVSHOW,
                categoryIds = listOf(18, 80),
                yearOfRelease = LocalDate(2008, 1, 20),
                rating = 9.5
            ),
            Media(
                id = 3,
                imageUri = "https://image.tmdb.org/t/p/w500/sample_movie_3.jpg",
                title = "Inception",
                type = MediaType.MOVIE,
                categoryIds = listOf(28, 878),
                yearOfRelease = LocalDate(2010, 7, 16),
                rating = 8.8
            ),
            Media(
                id = 4,
                imageUri = "https://image.tmdb.org/t/p/w500/sample_movie_4.jpg",
                title = "Stranger Things",
                type = MediaType.TVSHOW,
                categoryIds = listOf(18, 9648, 10765),
                yearOfRelease = LocalDate(2016, 7, 15),
                rating = 8.7
            )
        ).filter { it.type == mediaType }
    }
}