package com.repository.dataSource.local

import com.repository.model.local.TvShowCastEntity
import com.repository.model.local.TVShowGalleryEntity
import com.repository.model.local.TVShowReviewEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TvShowEntity
import com.repository.model.local.TvShowSimilarEntity


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