package com.repository.movie.testUtils

import com.repository.movie.mapper.toEntity
import com.repository.movie.models.local.ReviewEntity
import com.repository.movie.models.remote.MovieCastDto
import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MovieLogoDto
import com.repository.movie.models.remote.MovieSimilarDto
import com.repository.movie.models.remote.MovieSimilarsDto
import kotlinx.datetime.LocalDate

val mockMovieDto = MovieDto(
    id = 550,
    title = "The Epic Blockbuster",
    overview = "Mind-blowing action sequences",
    releaseDate = "2024-07-15"
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
    avatarUrl = "",
    username = "",
    rating = 2.2
).toEntity()