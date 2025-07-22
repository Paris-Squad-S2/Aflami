package com.repository.movie.testUtils

import com.repository.movie.models.local.ReviewEntity
import com.repository.movie.models.remote.MovieCastDto
import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MovieLogoDto
import com.repository.movie.models.remote.MovieSimilarDto
import com.repository.movie.models.remote.MovieSimilarsDto
import com.repository.movie.models.remote.MovieVideoDto
import com.repository.movie.models.remote.MovieVideoResultDto
import kotlinx.datetime.LocalDate

val mockMovieDto = MovieDto(
    id = 550,
    title = "The Epic Blockbuster",
    overview = "Mind-blowing action sequences",
    releaseDate = "2024-07-15"
)

val mockMovieVideosDto = MovieVideoDto(
    id = 550,
    movieVideoResultDto = listOf(
        MovieVideoResultDto(key = "dQw4w9WgXcQ", name = "Trailer 1", site = "YouTube", type = "Trailer"),
        MovieVideoResultDto(key = "abc123", name = "Trailer 2", site = "Vimeo", type = "Trailer")
    )
)

val mockMovieCreditsDto = MovieCreditsDto(
    id = 550,
    cast = listOf(
        MovieCastDto(
            name = "alex"
        )
    )
)

val mockMovieSimilarsDto = MovieSimilarsDto(
    movieSimilarDto = listOf(
        MovieSimilarDto(
            title = "batman"
        )
    )
)

val mockMovieImagesDto = MovieImagesDto(
    id = 550,
    logos = listOf(
        MovieLogoDto(filePath = "http://images.jpeg")
    )
)

val review = ReviewEntity(
    name = "mohammed",
    id = 0,
    movieId = 550,
    createdAt = LocalDate.parse("2025-01-01"),
    avatarUrl = "https://image.tmdb.org/t/p/w500/",
    username = "",
    rating = 2.2,
    description = "A thrilling ride from start to finish. The performances are outstanding, especially Bryan Cranston's portrayal of Walter White.",
    language = "AR"
)