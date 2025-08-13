package com.datasource.local.guessGame

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.datasource.local.guessGame.dao.GamePointsDao
import com.repository.guessgame.entity.UserGamePointsEntity

@Database(entities = [UserGamePointsEntity::class], version = 1)
abstract class GuessGameDatabase : RoomDatabase() {
    abstract fun gamePointsDao(): GamePointsDao

    companion object {
        const val DATABASE_NAME = "guess_game_db"

        @Volatile
        private var instance: GuessGameDatabase? = null

        fun getInstance(context: Context): GuessGameDatabase {
            return instance ?: synchronized(this) {
                buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): GuessGameDatabase {
            return Room.databaseBuilder(
                context = context,
                klass = GuessGameDatabase::class.java,
                name = DATABASE_NAME
            ).build()
        }
    }
}