package com.repository.dataSource.remote

import com.repository.model.remote.EpisodeVideoDto
import com.repository.model.remote.TvShowCreditsDto
import com.repository.model.remote.TvShowDto
import com.repository.model.remote.TvShowImagesDto
import com.repository.model.remote.TvShowReviewsDto
import com.repository.model.remote.TvShowSeasonDto
import com.repository.model.remote.TvShowSimilarsDto
import com.repository.model.remote.TvShowVideoDto

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
}