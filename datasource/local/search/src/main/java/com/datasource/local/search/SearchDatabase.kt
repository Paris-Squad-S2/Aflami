package com.datasource.local.search

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.search.dao.CountryDao
import com.datasource.local.search.dao.GenresDao
import com.datasource.local.search.dao.MediaDao
import com.datasource.local.search.dao.SearchHistoryDao
import com.repository.search.entity.CountryEntity
import com.repository.search.entity.GenreEntity
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.SearchHistoryEntity


@Database(
    entities = [SearchHistoryEntity::class, MediaEntity::class, CountryEntity::class, GenreEntity::class],
    version = 1,
)
@TypeConverters(SearchConverter::class)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun mediaDao(): MediaDao
    abstract fun countryDao(): CountryDao
    abstract fun genresDao(): GenresDao

    companion object {
        private const val DATABASE_NAME = "search_db"

        @Volatile
        private var instance: SearchDatabase? = null

        fun getInstance(context: Context): SearchDatabase {
            return instance ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        private fun buildDatabase(context: Context): SearchDatabase {
            return Room.databaseBuilder(context, SearchDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}