package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.model.local.TvShowCastEntity
import com.repository.model.local.TVShowGalleryEntity
import com.repository.model.local.TVShowReviewEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TvShowEntity
import com.repository.model.local.TvShowSimilarEntity

@Dao
interface TvShowDao {
    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addTvShow(tvShow: TvShowEntity)

    @Query("SELECT * FROM tv_shows_table WHERE id = :tvShowId AND language = :language")
    suspend fun getTvShowById(tvShowId: Int, language: String): TvShowEntity?

    @Query("DELETE FROM tv_shows_table WHERE id = :tvShowId AND language = :language")
    suspend fun clearTVShowDetailsById(tvShowId: Int, language: String)

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addTvShowSeason(season: SeasonEntity)

    @Query("SELECT * FROM seasons_table WHERE tvShowId = :tvShowId AND seasonNumber = :seasonNumber LIMIT 1")
    suspend fun getSeasonByTvShowIdAndSeasonNumber(tvShowId: Int, seasonNumber: Int): SeasonEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addTvShowCast(casts: List<TvShowCastEntity>)

    @Query("SELECT * FROM cast_tv_shows_table WHERE tvShowId = :tvShowId AND language = :language")
    suspend fun getCastByTvShowId(tvShowId: Int, language: String): List<TvShowCastEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addTvShowGallery(gallery: TVShowGalleryEntity)

    @Query("SELECT * FROM tv_gallery_table WHERE tvShowId = :tvShowId")
    suspend fun getGalleryByTvShowId(tvShowId: Int): TVShowGalleryEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addTvShowReviews(reviews: List<TVShowReviewEntity>)

    @Query("SELECT * FROM tv_reviews_table WHERE tvShowId = :tvShowId AND language = :language")
    suspend fun getReviewsByTvShowId(tvShowId: Int, language: String): List<TVShowReviewEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addSimilarTvShows(tvShows: List<TvShowSimilarEntity>)

    @Query("SELECT * FROM tv_shows_similar_table WHERE id = :tvShowId AND page = :page AND language = :language")
    suspend fun getSimilarTvShows(
        tvShowId: Int,
        page: Int,
        language: String
    ): List<TvShowSimilarEntity>
}