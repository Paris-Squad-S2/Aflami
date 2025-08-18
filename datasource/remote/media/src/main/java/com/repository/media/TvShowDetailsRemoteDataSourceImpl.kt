package com.repository.media

import com.repository.media.datasource.remote.TvShowDetailsRemoteDataSource
import com.repository.media.services.TvShowDetailsApiService
import com.repository.media.models.remote.tvShow.EpisodeVideoDto
import com.repository.media.models.remote.tvShow.TvShowCreditsDto
import com.repository.media.models.remote.tvShow.TvShowDto
import com.repository.media.models.remote.tvShow.TvShowImagesDto
import com.repository.media.models.remote.tvShow.TvShowReviewsDto
import com.repository.media.models.remote.tvShow.TvShowSeasonDto
import com.repository.media.models.remote.tvShow.TvShowSimilarsDto
import com.repository.media.models.remote.tvShow.TvShowVideoDto
import com.repository.media.models.remote.movie.RatingDto
import javax.inject.Inject

class TvShowDetailsRemoteDataSourceImpl @Inject constructor(
    private val tvShowDetailsApiService: TvShowDetailsApiService
) : TvShowDetailsRemoteDataSource {

    override suspend fun getTvShowDetails(tvShowId: Int, language: String): TvShowDto {
        return tvShowDetailsApiService.getTvShowDetails(tvShowId, language)
    }

    override suspend fun getTvShowImages(tvShowId: Int): TvShowImagesDto {
        return tvShowDetailsApiService.getTvShowImages(tvShowId)
    }

    override suspend fun getTvShowReviews(
        tvShowId: Int,
        page: Int,
        language: String
    ): TvShowReviewsDto {
        return tvShowDetailsApiService.getTvShowReviews(tvShowId, page, language)
    }

    override suspend fun getSimilarTvShows(
        tvShowId: Int,
        page: Int,
        language: String
    ): TvShowSimilarsDto {
        return tvShowDetailsApiService.getSimilarTvShows(tvShowId, page, language)
    }

    override suspend fun getTvShowCredits(tvShowId: Int, language: String): TvShowCreditsDto {
        return tvShowDetailsApiService.getTvShowCredits(tvShowId, language)
    }

    override suspend fun getSeasonDetails(
        tvShowId: Int,
        seasonNumber: Int,
        language: String
    ): TvShowSeasonDto {
        return tvShowDetailsApiService.getSeasonDetails(tvShowId, seasonNumber, language)
    }

    override suspend fun getTrailerVideoForTvShow(tvShowId: Int): TvShowVideoDto {
        return tvShowDetailsApiService.getTrailerVideoForTvShow(tvShowId)
    }

    override suspend fun getTrailerVideoForEpisode(
        tvShowId: Int,
        seasonNumber: Int,
        episodeNumber: Int,
        language: String
    ): EpisodeVideoDto {
        return tvShowDetailsApiService.getTrailerVideoForEpisode(
            tvShowId,
            seasonNumber,
            episodeNumber,
            language
        )
    }

    override suspend fun addRatingToTvShow(movieId: Int, rating: Float): Boolean {
        val dto = RatingDto(value = rating)
        val response = tvShowDetailsApiService.addRatingToTvShow(movieId, dto)
        return !(response.statusCode != STATUS_CODE_SUCCESS && response.statusCode != STATUS_CODE_UPDATED)
    }

    override suspend fun deleteTvShowRating(tvShowId : Int): Boolean {
        return tvShowDetailsApiService.deleteTvShowRating(tvShowId).success
    }

    companion object {
        private const val STATUS_CODE_SUCCESS = 1
        private const val STATUS_CODE_UPDATED = 12
    }
}