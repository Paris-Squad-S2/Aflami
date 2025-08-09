package com.repository.dataSource.local

import com.repository.model.local.CastEntity
import com.repository.model.local.GalleryEntity
import com.repository.model.local.ReviewEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TvShowEntity
import com.repository.model.local.TvShowSimilarEntity


interface TvShowLocalDataSource {
    suspend fun addTvShow(tvShow: TvShowEntity)
    suspend fun getTvShowId(tvShowId: Int,language: String): TvShowEntity?
    suspend fun clearTvShowById(tvShowId: Int, language: String)
    suspend fun addTvShowCast(cast: List<CastEntity>)
    suspend fun getCastByTvShowId(tvShowId: Int,language: String): List<CastEntity>
    suspend fun addTvShowGallery(gallery: GalleryEntity)
    suspend fun getGalleryByTvShowId(tvShowId: Int): GalleryEntity?
    suspend fun addTvShowReviews(reviews: List<ReviewEntity>)
    suspend fun getReviewsByTvShowId(tvShowId: Int,language: String): List<ReviewEntity>
    suspend fun addTvShowSeason(seasons: SeasonEntity)
    suspend fun getSeasonByTvShowIdAndSeasonNumber(tvShowId: Int, seasonNumber: Int): SeasonEntity?
    suspend fun addSimilarTvShows(tvShowSimilar: List<TvShowSimilarEntity>)
    suspend fun getSimilarTvShows(
        tvShowId: Int,
        page: Int,
        language: String
    ): List<TvShowSimilarEntity>
}