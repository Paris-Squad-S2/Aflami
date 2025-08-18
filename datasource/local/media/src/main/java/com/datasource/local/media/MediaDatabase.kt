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
import com.repository.media.entity.CountryEntity
import com.repository.media.entity.GenreUserInteractionEntity
import com.repository.media.entity.HomeMediaEntity
import com.repository.media.entity.MediaEntity
import com.repository.media.entity.SearchHistoryEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TVShowGalleryEntity
import com.repository.model.local.TVShowReviewEntity
import com.repository.model.local.TvShowCastEntity
import com.repository.model.local.TvShowEntity
import com.repository.model.local.TvShowSimilarEntity
import com.repository.movie.models.local.MovieCastEntity
import com.repository.movie.models.local.MovieGalleryEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.MovieSimilarEntity
import com.repository.movie.models.local.MovieReviewEntity

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