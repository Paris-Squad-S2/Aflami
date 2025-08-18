package com.repository.media.repository

import com.repository.media.models.local.moive.MovieReviewEntity
import com.repository.media.models.remote.movie.MovieDto
import com.repository.media.models.remote.movie.MovieCastDto
import com.repository.media.models.remote.movie.MovieCreditsDto
import com.repository.media.models.remote.movie.MovieImagesDto
import com.repository.media.models.remote.movie.MovieLogoDto
import com.repository.media.models.remote.movie.MovieReviewDto
import com.repository.media.models.remote.movie.MovieSimilarDto
import com.repository.media.models.remote.movie.MovieSimilarsDto
import com.repository.media.models.remote.movie.MovieVideoDto
import com.repository.media.models.remote.movie.MovieVideoResultDto
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

val review = MovieReviewEntity(
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

val reviewRemoteDto = MovieReviewDto(
  author = "mohammed",
)