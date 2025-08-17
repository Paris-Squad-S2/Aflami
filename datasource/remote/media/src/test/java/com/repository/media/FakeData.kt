package com.repository.media

import com.repository.movie.models.remote.MovieCastDto
import com.repository.movie.models.remote.MovieCreditsDto
import com.repository.movie.models.remote.MovieDto
import com.repository.movie.models.remote.MovieGenreDto
import com.repository.movie.models.remote.MovieImagesDto
import com.repository.movie.models.remote.MoviePosterDto
import com.repository.movie.models.remote.MovieReviewDto
import com.repository.movie.models.remote.MovieReviewsDto
import com.repository.movie.models.remote.MovieSimilarDto
import com.repository.movie.models.remote.MovieSimilarsDto
import com.repository.movie.models.remote.MovieVideoDto
import com.repository.movie.models.remote.MovieVideoResultDto

val genres = listOf(
    MovieGenreDto(
        id = 1,
        name = "Drama"
    )
)

val movieDetails = MovieDto(
    id = 123,
    title = "Sample TV Show",
    overview = "This is a sample overview of the TV show.",
    movieGenreDto = genres,
    voteAverage = 8.5,
    voteCount = 1000,
    posterPath = "/sample_poster.jpg",
    backdropPath = "/sample_backdrop.jpg"
)

val movieImages = MovieImagesDto(
    posters = listOf(
        MoviePosterDto(
            filePath = "/sample_poster.jpg",
            aspectRatio = 1.78,
            height = 1080,
            width = 1920
        )
    )
)

val movieReview = MovieReviewsDto(
    results = listOf(
        MovieReviewDto(
            author = "John Doe",
            content = "This is a sample review of the TV show.",
            createdAt = "2023-01-02"
        )
    )
)

val movieSimilarDto = MovieSimilarsDto(
    page = 1,
    movieSimilarDto = listOf(MovieSimilarDto(title = "batman"))
)

val movieCreditsDto = MovieCreditsDto(
    cast = listOf(MovieCastDto(name = "John Doe", character = "Main Character"))
)

val movieVideoDto = MovieVideoDto(
    movieVideoResultDto = listOf(MovieVideoResultDto(name = "Sample Trailer", key = "sample"))
)

