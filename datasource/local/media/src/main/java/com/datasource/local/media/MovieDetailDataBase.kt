package com.datasource.local.media

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.media.dao.MovieDao
import com.repository.movie.models.local.CastEntity
import com.repository.movie.models.local.GalleryEntity
import com.repository.movie.models.local.MovieEntity
import com.repository.movie.models.local.MovieSimilarEntity
import com.repository.movie.models.local.ReviewEntity

@Database(
    entities = [MovieEntity::class, CastEntity::class, ReviewEntity::class, GalleryEntity::class, MovieSimilarEntity::class],
    version = 1
)
@TypeConverters(MovieDetailConverter::class)
abstract class MovieDetailDataBase : RoomDatabase() {
    abstract fun movieDao(): MovieDao

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