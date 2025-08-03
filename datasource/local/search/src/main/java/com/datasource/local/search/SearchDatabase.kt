package com.datasource.local.search

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.datasource.local.search.dao.CountryDao
import com.datasource.local.search.dao.GenresUserInteractionDao
import com.datasource.local.search.dao.SearchHistoryDao
import com.repository.search.entity.CountryEntity
import com.repository.search.entity.GenreUserInteractionEntity
import com.repository.search.entity.SearchHistoryEntity


@Database(
    entities = [SearchHistoryEntity::class, CountryEntity::class, GenreUserInteractionEntity::class],
    version = 2,
)
@TypeConverters(SearchConverter::class)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun countryDao(): CountryDao
    abstract fun genreUserInteractionDao(): GenresUserInteractionDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Genres_table ADD COLUMN language TEXT NOT NULL DEFAULT 'en'")
            }
        }
    }
}