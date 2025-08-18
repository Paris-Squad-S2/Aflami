package com.repository.media

import com.repository.media.models.remote.tvShow.TvShowCastDto
import com.repository.media.models.remote.tvShow.TvShowCreditsDto
import com.repository.media.models.remote.tvShow.TvShowDto
import com.repository.media.models.remote.tvShow.TvShowGenreDto
import com.repository.media.models.remote.tvShow.TvShowImagesDto
import com.repository.media.models.remote.tvShow.TvShowPosterDto
import com.repository.media.models.remote.tvShow.TvShowReviewDto
import com.repository.media.models.remote.tvShow.TvShowReviewsDto
import com.repository.media.models.remote.tvShow.TvShowSeasonDto
import com.repository.media.models.remote.tvShow.TvShowSimilarDto
import com.repository.media.models.remote.tvShow.TvShowSimilarsDto
import com.repository.media.models.remote.tvShow.TvShowVideoDto
import com.repository.media.models.remote.tvShow.TvShowVideoResultDto

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

