package com.datasource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.dao.MovieCastDao
import com.datasource.local.dao.GalleryDao
import com.datasource.local.dao.MovieDao
import com.datasource.local.dao.MovieReviewDao
import com.repository.movie.models.local.CastEntity
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.ReviewEntity

@Database(
    entities = [MovieEntity::class, CastEntity::class, ReviewEntity::class, GalleryEntity::class],
    version = 1
)
@TypeConverters(MovieDetailConverter::class)
abstract class MovieDetailDataBase : RoomDatabase() {

    abstract fun castDao(): MovieCastDao
    abstract fun galleryDao(): GalleryDao
    abstract fun movieDao(): MovieDao
    abstract fun reviewDao(): MovieReviewDao


    companion object {
        const val DATABASE_NAME = "movie_detail_db"

        @Volatile
        private var instance: MovieDetailDataBase? = null

        fun getInstance(context: Context): MovieDetailDataBase {
            return instance ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        private fun buildDatabase(context: Context): MovieDetailDataBase {
            return Room.databaseBuilder(context, MovieDetailDataBase::class.java, DATABASE_NAME)
                .build()
        }
    }

}