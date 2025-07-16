package com.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.datasource.local.dao.CastDao
import com.datasource.local.dao.GalleryDao
import com.datasource.local.dao.ReviewDao
import com.datasource.local.dao.SeasonDao
import com.datasource.local.dao.TvShowDao
import com.repository.entity.CastEntity
import com.repository.entity.GalleryEntity
import com.repository.entity.ReviewEntity
import com.repository.entity.SeasonEntity
import com.repository.entity.TvShowEntity

@Database(
    entities = [
        TvShowEntity::class, SeasonEntity::class,
        CastEntity::class, ReviewEntity::class,
        GalleryEntity::class],
    version = 1
)
abstract class TvShowDetailDataBase : RoomDatabase() {

    abstract fun castDao(): CastDao
    abstract fun galleryDao(): GalleryDao
    abstract fun movieDao(): TvShowDao
    abstract fun reviewDao(): ReviewDao
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