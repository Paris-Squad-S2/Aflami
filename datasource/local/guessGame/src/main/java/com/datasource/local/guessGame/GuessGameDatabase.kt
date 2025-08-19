package com.datasource.local.guessGame

import androidx.room.Database
import androidx.room.RoomDatabase
import com.datasource.local.guessGame.dao.GamePointsDao
import com.repository.guessgame.entity.UserGamePointsEntity

@Database(entities = [UserGamePointsEntity::class], version = 1)
abstract class GuessGameDatabase : RoomDatabase() {
    abstract fun gamePointsDao(): GamePointsDao
}