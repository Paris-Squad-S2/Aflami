package com.datasource.local.media

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.media.dao.CountryDao
import com.datasource.local.media.dao.GenresUserInteractionDao
import com.datasource.local.media.dao.MediaDao
import com.datasource.local.media.dao.MovieDao
import com.datasource.local.media.dao.SearchHistoryDao
import com.datasource.local.media.dao.TvShowDao
import com.repository.media.models.local.media.CountryEntity
import com.repository.media.models.local.media.GenreUserInteractionEntity
import com.repository.media.models.local.media.HomeMediaEntity
import com.repository.media.models.local.media.MediaEntity
import com.repository.media.models.local.media.SearchHistoryEntity
import com.repository.media.models.local.moive.MovieCastEntity
import com.repository.media.models.local.moive.MovieEntity
import com.repository.media.models.local.moive.MovieGalleryEntity
import com.repository.media.models.local.moive.MovieReviewEntity
import com.repository.media.models.local.moive.MovieSimilarEntity
import com.repository.media.models.local.tvShow.SeasonEntity
import com.repository.media.models.local.tvShow.TVShowGalleryEntity
import com.repository.media.models.local.tvShow.TVShowReviewEntity
import com.repository.media.models.local.tvShow.TvShowCastEntity
import com.repository.media.models.local.tvShow.TvShowEntity
import com.repository.media.models.local.tvShow.TvShowSimilarEntity

@Database(
    entities = [MediaEntity::class, HomeMediaEntity::class, MovieEntity::class,
        MovieCastEntity::class, MovieReviewEntity::class, MovieGalleryEntity::class,
        MovieSimilarEntity::class, SearchHistoryEntity::class, CountryEntity::class,
        GenreUserInteractionEntity::class, TvShowEntity::class, SeasonEntity::class,
        TvShowCastEntity::class, TVShowReviewEntity::class, TVShowGalleryEntity::class,
        TvShowSimilarEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(MediaConverter::class)
abstract class MediaDatabase : RoomDatabase() {
    abstract fun mediaDao(): MediaDao
    abstract fun movieDao(): MovieDao
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun countryDao(): CountryDao
    abstract fun genreUserInteractionDao(): GenresUserInteractionDao
    abstract fun tvShowDao(): TvShowDao
}