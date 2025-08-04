package com.datasource.local.media

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.datasource.local.media.dao.CountryDao
import com.datasource.local.media.dao.GenresDao
import com.datasource.local.media.dao.GenresUserInteractionDao
import com.datasource.local.media.dao.MediaDao
import com.datasource.local.media.dao.SearchHistoryDao
import com.repository.media.entity.CountryEntity
import com.repository.media.entity.GenreEntity
import com.repository.media.entity.GenreUserInteractionEntity
import com.repository.media.entity.MediaSearchEntity
import com.repository.media.entity.SearchHistoryEntity


@Database(
    entities = [SearchHistoryEntity::class, MediaSearchEntity::class, CountryEntity::class, GenreEntity::class, GenreUserInteractionEntity::class],
    version = 2,
)
@TypeConverters(SearchConverter::class)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchHistoryDao(): SearchHistoryDao
    abstract fun mediaDao(): MediaDao
    abstract fun countryDao(): CountryDao
    abstract fun genresDao(): GenresDao
    abstract fun genreUserInteractionDao(): GenresUserInteractionDao

    companion object {

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE Genres_table ADD COLUMN language TEXT NOT NULL DEFAULT 'en'")
            }
        }
    }
}