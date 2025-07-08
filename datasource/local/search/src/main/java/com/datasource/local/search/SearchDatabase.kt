package com.datasource.local.search

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.search.dao.MediaDao
import com.datasource.local.search.dao.SearchHistoryDao
import com.repository.search.entity.MediaEntity
import com.repository.search.entity.SearchHistoryEntity

@Database(
    entities = [com.repository.search.entity.SearchHistoryEntity::class, com.repository.search.entity.MediaEntity::class],
    version = 1,
)
@TypeConverters(SearchConverter::class)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchHistoryDao
    abstract fun mediaDao(): MediaDao
}