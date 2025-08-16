package com.datasource.local.media.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.repository.model.local.CastEntity
import com.repository.model.local.GalleryEntity
import com.repository.model.local.ReviewEntity
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
    suspend fun addTvShowCast(casts: List<CastEntity>)

    @Query("SELECT * FROM cast_tv_shows_table WHERE tvShowId = :tvShowId AND language = :language")
    suspend fun getCastByTvShowId(tvShowId: Int, language: String): List<CastEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addTvShowGallery(gallery: GalleryEntity)

    @Query("SELECT * FROM gallery_table WHERE tvShowId = :tvShowId")
    suspend fun getGalleryByTvShowId(tvShowId: Int): GalleryEntity?

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addTvShowReviews(reviews: List<ReviewEntity>)

    @Query("SELECT * FROM reviews_table WHERE tvShowId = :tvShowId AND language = :language")
    suspend fun getReviewsByTvShowId(tvShowId: Int, language: String): List<ReviewEntity>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    suspend fun addSimilarTvShows(tvShows: List<TvShowSimilarEntity>)

    @Query("SELECT * FROM tv_shows_similar_table WHERE id = :tvShowId AND page = :page AND language = :language")
    suspend fun getSimilarTvShows(
        tvShowId: Int,
        page: Int,
        language: String
    ): List<TvShowSimilarEntity>
}