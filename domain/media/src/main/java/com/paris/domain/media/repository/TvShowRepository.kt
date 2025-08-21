package com.paris.domain.media.repository

import com.paris.domain.media.entity.Cast
import com.paris.domain.media.entity.Image
import com.paris.domain.media.entity.MediaVideo
import com.paris.domain.media.entity.ProductionCompany
import com.paris.domain.media.entity.Review
import com.paris.domain.media.entity.Season
import com.paris.domain.media.entity.TvShow
import com.paris.domain.media.entity.TvShowSimilar

interface TvShowRepository {
    suspend fun getTvShowDetails(tvShowId: Int): TvShow
    suspend fun getTvShowCast(tvShowId: Int): List<Cast>
    suspend fun getTvShowRecommendations(tvShowId: Int,page: Int): List<TvShowSimilar>
    suspend fun getTvShowGallery(tvShowId: Int): List<Image>
    suspend fun getCompanyProducts(tvShowId: Int): List<ProductionCompany>
    suspend fun getSeasonDetails(tvShowId: Int, seasonNumber: Int): Season
    suspend fun getTvShowReview(tvShowId: Int,page: Int): List<Review>
    suspend fun getTrailerVideoForTvShow(tvShowId: Int): List<MediaVideo>
    suspend fun getTrailerVideoForEpisode(
        tvShowId: Int, seasonNumber: Int,
        episodeNumber: Int,
    ): List<MediaVideo>
    suspend fun addRatingToTvShow(movieId: Int, rating: Float)

    suspend fun deleteTvShowRating(tvShowId: Int)
}