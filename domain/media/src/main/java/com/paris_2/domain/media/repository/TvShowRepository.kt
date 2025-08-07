package com.paris_2.domain.media.repository

import com.paris_2.domain.media.entity.Cast
import com.paris_2.domain.media.entity.EpisodeVideo
import com.paris_2.domain.media.entity.Image
import com.paris_2.domain.media.entity.ProductionCompany
import com.paris_2.domain.media.entity.Review
import com.paris_2.domain.media.entity.Season
import com.paris_2.domain.media.entity.TvShow
import com.paris_2.domain.media.entity.TvShowSimilar
import com.paris_2.domain.media.entity.TvShowVideo

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

    suspend fun deleteTvShowRating(tvShowId: Int)
}