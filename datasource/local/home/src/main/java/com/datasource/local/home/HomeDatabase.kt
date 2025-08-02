package com.datasource.local.home

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.home.dao.HomeMediaDao
import com.repository.media.entity.MediaEntity

@Database(
    entities = [MediaEntity::class],
    version = 1,
)

@TypeConverters(HomeConverter::class)
abstract class HomeDatabase : RoomDatabase() {
    abstract fun mediaDao(): HomeMediaDao

    companion object {
        const val DATABASE_NAME = "home_db"

        @Volatile
        private var instance: HomeDatabase? = null

        fun getInstance(context: Context): HomeDatabase {
            return instance
                ?: synchronized(this) { buildDatabase(context).also { instance = it } }
        }

        private fun buildDatabase(context: Context): HomeDatabase {
            return Room.databaseBuilder(
                context,
                HomeDatabase::class.java,
                DATABASE_NAME
            )
                .build()
        }

    }
}