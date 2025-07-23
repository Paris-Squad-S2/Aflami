package com.datasource.local.home

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.home.dao.MediaDao
import com.repository.home.entity.MediaEntity

@Database(
    entities = [MediaEntity::class],
    version = 1,
)

@TypeConverters(HomeConverter::class)
abstract class HomeDatabase: RoomDatabase() {
    abstract fun mediaDao(): MediaDao
}