package com.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.dao.SeasonDao
import com.datasource.local.dao.TvShowCastDao
import com.datasource.local.dao.TvShowDao
import com.datasource.local.dao.TvShowGalleryDao
import com.datasource.local.dao.TvShowReviewDao
import com.datasource.local.dao.TvShowSimilarDao
import com.repository.model.local.CastEntity
import com.repository.model.local.GalleryEntity
import com.repository.model.local.ReviewEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TvShowEntity
import com.repository.model.local.TvShowSimilarEntity

@Database(
    entities = [
        TvShowEntity::class, SeasonEntity::class,
        CastEntity::class, ReviewEntity::class,
        GalleryEntity::class, TvShowSimilarEntity::class],
    version = 1
)
@TypeConverters(TvShowDetailConverter::class)
abstract class TvShowDetailDataBase : RoomDatabase() {

    abstract fun castDao(): TvShowCastDao
    abstract fun galleryDao(): TvShowGalleryDao
    abstract fun movieDao(): TvShowDao
    abstract fun reviewDao(): TvShowReviewDao
    abstract fun seasonDao(): SeasonDao
    abstract fun tvShowSimilarDao(): TvShowSimilarDao
}