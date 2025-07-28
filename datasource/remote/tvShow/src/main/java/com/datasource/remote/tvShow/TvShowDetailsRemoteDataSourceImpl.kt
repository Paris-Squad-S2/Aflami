package com.datasource.remote.tvShow

import com.datasource.remote.tvShow.service.RetrofitTvShowDetailsApiService
import com.repository.dataSource.remote.TvShowDetailsRemoteDataSource
import com.repository.model.remote.EpisodeVideoDto
import com.repository.model.remote.TvShowCreditsDto
import com.repository.model.remote.TvShowDto
import com.repository.model.remote.TvShowImagesDto
import com.repository.model.remote.TvShowReviewsDto
import com.repository.model.remote.TvShowSeasonDto
import com.repository.model.remote.TvShowSimilarsDto
import com.repository.model.remote.TvShowVideoDto
import com.repository.movie.models.remote.RatingDto
import javax.inject.Inject

class TvShowDetailsRemoteDataSourceImpl @Inject constructor(
    private val retrofitTvShowDetailsApiService: RetrofitTvShowDetailsApiService
) : TvShowDetailsRemoteDataSource {

    override suspend fun getTvShowDetails(tvShowId: Int, language: String): TvShowDto {
        return retrofitTvShowDetailsApiService.getTvShowDetails(tvShowId, language)
    }

    override suspend fun getTvShowImages(tvShowId: Int): TvShowImagesDto {
        return retrofitTvShowDetailsApiService.getTvShowImages(tvShowId)
    }

    override suspend fun getTvShowReviews(
        tvShowId: Int,
        page: Int,
        language: String
    ): TvShowReviewsDto {
        return retrofitTvShowDetailsApiService.getTvShowReviews(tvShowId, page, language)
    }

    override suspend fun getSimilarTvShows(
        tvShowId: Int,
        page: Int,
        language: String
    ): TvShowSimilarsDto {
        return retrofitTvShowDetailsApiService.getSimilarTvShows(tvShowId, page, language)
    }

    override suspend fun getTvShowCredits(tvShowId: Int, language: String): TvShowCreditsDto {
        return retrofitTvShowDetailsApiService.getTvShowCredits(tvShowId, language)
    }

    override suspend fun getSeasonDetails(
        tvShowId: Int,
        seasonNumber: Int,
        language: String
    ): TvShowSeasonDto {
        return retrofitTvShowDetailsApiService.getSeasonDetails(tvShowId, seasonNumber, language)
    }

    override suspend fun getTrailerVideoForTvShow(tvShowId: Int): TvShowVideoDto {
        return retrofitTvShowDetailsApiService.getTrailerVideoForTvShow(tvShowId)
    }

    override suspend fun getTrailerVideoForEpisode(
        tvShowId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        language: String
    ): EpisodeVideoDto {
        return retrofitTvShowDetailsApiService.getTrailerVideoForEpisode(
            tvShowId,
            seasonNumber,
            episodeNumber,
            language
        )
    }

    override suspend fun addRatingToTvShow(
        movieId: Int,
        rating: Float,
        sessionId: String
    ) {
        val dto = RatingDto(value = rating)
        val response = retrofitTvShowDetailsApiService.addRatingToTvShow(movieId, sessionId, dto)
        if (response.status_code != 1 && response.status_code != 12) {
            throw Exception("Server responded: ${response.status_message}")
        }
    }
}
