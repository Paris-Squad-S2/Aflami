package com.datasource.local.media

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.datasource.local.media.dao.ContinueWatchingDao
import com.datasource.local.media.dao.HomeMediaDao
import com.repository.media.entity.MediaEntity
import com.repository.media.entity.HomeMediaEntity

@Database(
    entities = [MediaEntity::class,HomeMediaEntity::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(HomeConverter::class)
abstract class HomeDatabase : RoomDatabase() {
    abstract fun continueWatchingDao(): ContinueWatchingDao
    abstract fun homeMediaDao(): HomeMediaDao

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