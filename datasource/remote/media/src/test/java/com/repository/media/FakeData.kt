package com.repository.media

import com.repository.media.models.remote.movie.MovieCastDto
import com.repository.media.models.remote.movie.MovieCreditsDto
import com.repository.media.models.remote.movie.MovieDto
import com.repository.media.models.remote.movie.MovieGenreDto
import com.repository.media.models.remote.movie.MovieImagesDto
import com.repository.media.models.remote.movie.MoviePosterDto
import com.repository.media.models.remote.movie.MovieReviewDto
import com.repository.media.models.remote.movie.MovieReviewsDto
import com.repository.media.models.remote.movie.MovieSimilarDto
import com.repository.media.models.remote.movie.MovieSimilarsDto
import com.repository.media.models.remote.movie.MovieVideoDto
import com.repository.media.models.remote.movie.MovieVideoResultDto

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

