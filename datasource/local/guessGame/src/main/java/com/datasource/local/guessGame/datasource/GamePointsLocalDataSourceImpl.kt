package com.datasource.local.guessGame.datasource

import com.datasource.local.guessGame.dao.GamePointsDao
import com.repository.guessgame.datasource.local.GamePointsLocalDataSource
import com.repository.guessgame.entity.UserGamePointsEntity

class GamePointsLocalDataSourceImpl(
   private val gamePointsDao: GamePointsDao
) : GamePointsLocalDataSource {
    override suspend fun saveUserGamePoints(gamePoints: UserGamePointsEntity) {
        gamePointsDao.upsertUserGamePoints(gamePoints)
    }
}