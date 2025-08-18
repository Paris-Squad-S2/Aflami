package com.repository.media.datasource.local

import com.repository.media.models.local.tvShow.SeasonEntity
import com.repository.media.models.local.tvShow.TVShowGalleryEntity
import com.repository.media.models.local.tvShow.TVShowReviewEntity
import com.repository.media.models.local.tvShow.TvShowCastEntity
import com.repository.media.models.local.tvShow.TvShowEntity
import com.repository.media.models.local.tvShow.TvShowSimilarEntity

interface TvShowLocalDataSource {
    suspend fun addTvShow(tvShow: TvShowEntity)
    suspend fun getTvShowId(tvShowId: Int,language: String): TvShowEntity?
    suspend fun clearTvShowById(tvShowId: Int, language: String)
    suspend fun addTvShowCast(cast: List<TvShowCastEntity>)
    suspend fun getCastByTvShowId(tvShowId: Int,language: String): List<TvShowCastEntity>
    suspend fun addTvShowGallery(gallery: TVShowGalleryEntity)
    suspend fun getGalleryByTvShowId(tvShowId: Int): TVShowGalleryEntity?
    suspend fun addTvShowReviews(reviews: List<TVShowReviewEntity>)
    suspend fun getReviewsByTvShowId(tvShowId: Int,language: String): List<TVShowReviewEntity>
    suspend fun addTvShowSeason(seasons: SeasonEntity)
    suspend fun getSeasonByTvShowIdAndSeasonNumber(tvShowId: Int, seasonNumber: Int): SeasonEntity?
    suspend fun addSimilarTvShows(tvShowSimilar: List<TvShowSimilarEntity>)
    suspend fun getSimilarTvShows(
        tvShowId: Int,
        page: Int,
        language: String
    ): List<TvShowSimilarEntity>
}