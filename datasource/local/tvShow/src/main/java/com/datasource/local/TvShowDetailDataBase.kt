package com.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.dao.TvShowCastDao
import com.datasource.local.dao.TvShowGalleryDao
import com.datasource.local.dao.TvShowReviewDao
import com.datasource.local.dao.SeasonDao
import com.datasource.local.dao.TvShowDao
import com.repository.model.local.CastEntity
import com.repository.model.local.GalleryEntity
import com.repository.model.local.ReviewEntity
import com.repository.model.local.SeasonEntity
import com.repository.model.local.TvShowEntity

@Database(
    entities = [
        TvShowEntity::class, SeasonEntity::class,
        CastEntity::class, ReviewEntity::class,
        GalleryEntity::class],
    version = 1
)
@TypeConverters(TvShowDetailConverter::class)
abstract class TvShowDetailDataBase : RoomDatabase() {

    abstract fun castDao(): TvShowCastDao
    abstract fun galleryDao(): TvShowGalleryDao
    abstract fun movieDao(): TvShowDao
    abstract fun reviewDao(): TvShowReviewDao
    abstract fun seasonDao(): SeasonDao

    companion object {
        const val DATABASE_NAME = "tv_shows_detail_db"

        @Volatile
        private var instance: TvShowDetailDataBase? = null

        fun getInstance(context: Context): TvShowDetailDataBase {
            return instance ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        private fun buildDatabase(context: Context): TvShowDetailDataBase {
            return Room.databaseBuilder(context, TvShowDetailDataBase::class.java, DATABASE_NAME)
                .build()
        }
    }

}