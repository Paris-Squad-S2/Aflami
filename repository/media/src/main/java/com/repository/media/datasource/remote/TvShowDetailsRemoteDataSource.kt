package com.repository.media.datasource.remote

import com.repository.media.models.remote.tvShow.EpisodeVideoDto
import com.repository.media.models.remote.tvShow.TvShowCreditsDto
import com.repository.media.models.remote.tvShow.TvShowDto
import com.repository.media.models.remote.tvShow.TvShowImagesDto
import com.repository.media.models.remote.tvShow.TvShowReviewsDto
import com.repository.media.models.remote.tvShow.TvShowSeasonDto
import com.repository.media.models.remote.tvShow.TvShowSimilarsDto
import com.repository.media.models.remote.tvShow.TvShowVideoDto

interface TvShowDetailsRemoteDataSource {
    suspend fun getTvShowDetails(tvShowId: Int, language: String): TvShowDto
    suspend fun getTvShowImages(tvShowId: Int): TvShowImagesDto
    suspend fun getTvShowReviews(tvShowId: Int, page: Int, language: String): TvShowReviewsDto
    suspend fun getSimilarTvShows(tvShowId: Int, page: Int, language: String): TvShowSimilarsDto
    suspend fun getTvShowCredits(tvShowId: Int, language: String): TvShowCreditsDto
    suspend fun getSeasonDetails(
        tvShowId: Int,
        seasonNumber: Int,
        language: String
    ): TvShowSeasonDto
    suspend fun getTrailerVideoForTvShow(tvShowId: Int): TvShowVideoDto
    suspend fun getTrailerVideoForEpisode(
        tvShowId: Int, seasonNumber: Int,
        episodeNumber: Int,
        language: String
    ): EpisodeVideoDto
    suspend fun addRatingToTvShow(movieId: Int, rating: Float): Boolean
    suspend fun deleteTvShowRating(tvShowId : Int): Boolean
}