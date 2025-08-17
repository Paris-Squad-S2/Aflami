package com.repository.media

import com.repository.model.remote.TvShowCastDto
import com.repository.model.remote.TvShowCreditsDto
import com.repository.model.remote.TvShowDto
import com.repository.model.remote.TvShowGenreDto
import com.repository.model.remote.TvShowImagesDto
import com.repository.model.remote.TvShowPosterDto
import com.repository.model.remote.TvShowReviewDto
import com.repository.model.remote.TvShowReviewsDto
import com.repository.model.remote.TvShowSeasonDto
import com.repository.model.remote.TvShowSimilarDto
import com.repository.model.remote.TvShowSimilarsDto
import com.repository.model.remote.TvShowVideoDto
import com.repository.model.remote.TvShowVideoResultDto

val tvShowGenres = listOf(
    TvShowGenreDto(
        id = 1,
        name = "Drama"
    )
)

val tvShowDetails = TvShowDto(
    id = 123,
    name = "Sample TV Show",
    overview = "This is a sample overview of the TV show.",
    firstAirDate = "2023-01-01",
    genres = tvShowGenres,
    voteAverage = 8.5,
    voteCount = 1000,
    posterPath = "/sample_poster.jpg",
    backdropPath = "/sample_backdrop.jpg"
)

val tvShowImages = TvShowImagesDto(
    posters = listOf(
        TvShowPosterDto(
            filePath = "/sample_poster.jpg",
            aspectRatio = 1.78,
            height = 1080,
            width = 1920
        )
    )
)

val tvShowReview = TvShowReviewsDto(
    results = listOf(
        TvShowReviewDto(
            author = "John Doe",
            content = "This is a sample review of the TV show.",
            createdAt = "2023-01-02"
        )
    )
)

val tvShowSimilarDto = TvShowSimilarsDto(
    page = 1,
    tvShowSimilarDto = listOf(TvShowSimilarDto(title = "batman"))
)

val tvShowCreditsDto = TvShowCreditsDto(
    cast = listOf(TvShowCastDto(name = "John Doe", character = "Main Character"))
)

val tvShowSeasonDetails = TvShowSeasonDto(
    name = "Sample Season",
)

val tvShowVideoDto = TvShowVideoDto(
    tvShowVideoResultDto = listOf(TvShowVideoResultDto(name = "Sample Trailer", key = "sample"))
)

