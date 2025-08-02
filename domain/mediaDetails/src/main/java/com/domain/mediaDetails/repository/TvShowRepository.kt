package com.domain.mediaDetails.repository

import com.domain.media.entity.Cast
import com.domain.media.entity.EpisodeVideo
import com.domain.media.entity.Image
import com.domain.media.entity.ProductionCompany
import com.domain.media.entity.Review
import com.domain.media.entity.Season
import com.domain.media.entity.TvShow
import com.domain.media.entity.TvShowSimilar
import com.domain.media.entity.TvShowVideo

interface TvShowRepository {
    suspend fun getTvShowDetails(tvShowId: Int): TvShow
    suspend fun getTvShowCast(tvShowId: Int): List<Cast>
    suspend fun getTvShowRecommendations(tvShowId: Int,page: Int): List<TvShowSimilar>
    suspend fun getTvShowGallery(tvShowId: Int): List<Image>
    suspend fun getCompanyProducts(tvShowId: Int): List<ProductionCompany>
    suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int): Season
    suspend fun getTvShowReview(tvShowId: Int,page: Int): List<Review>
    suspend fun getTrailerVideoForTvShow(tvShowId: Int): List<TvShowVideo>
    suspend fun getTrailerVideoForEpisode(
        tvShowId: Int, seasonNumber: Int,
        episodeNumber: Int,
    ): List<EpisodeVideo>
    suspend fun addRatingToTvShow(movieId: Int, rating: Float)
}