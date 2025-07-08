package com.datasource.local.search

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.search.dao.MediaDao
import com.datasource.local.search.dao.SearchDao
import com.datasource.local.search.entity.MediaEntity
import com.datasource.local.search.entity.SearchEntity

@Database(
    entities = [SearchEntity::class, MediaEntity::class],
    version = 1,
)
@TypeConverters(SearchConverter::class)
abstract class SearchDatabase : RoomDatabase() {
    abstract fun searchDao(): SearchDao
    abstract fun mediaDao(): MediaDao
}