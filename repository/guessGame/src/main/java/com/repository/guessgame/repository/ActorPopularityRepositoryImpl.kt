package com.repository.guessgame.repository

import com.paris.domain.game.entity.Actor
import com.paris.domain.game.exception.FailedException
import com.paris.domain.game.repositories.ActorPopularityRepository
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.guessgame.datasource.remote.ActorPopularityRemoteDataSource
import com.repository.guessgame.mapper.toDomain
import com.repository.guessgame.utils.NetworkConnectionChecker
import com.repository.guessgame.utils.safeCall
import kotlinx.coroutines.flow.first

class ActorPopularityRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val actorPopularityDataSource: ActorPopularityRemoteDataSource,
    private val settingLocalDataSource: SettingLocalDataSource

) : ActorPopularityRepository {
    override suspend fun getPopularActor(): List<Actor> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("Failed to get popular actor"), networkConnectionChecker) {
            actorPopularityDataSource.getPopularActors(language)
                .results
                ?.mapNotNull { it?.toDomain() }
                ?: emptyList()
        }
    }

    override suspend fun getRandomActors(numberOfActors: Int): List<Actor> {
        val language = settingLocalDataSource.getLanguage().first()
        return safeCall(FailedException("Failed to get random actors"), networkConnectionChecker) {
            val allActors = actorPopularityDataSource
                .getPopularActors(language)
                .results
                ?.mapNotNull { it?.toDomain() }
                ?: emptyList()

            require(allActors.size >= numberOfActors) {
                "Not enough actors available to provide $numberOfActors"
            }
            allActors.shuffled().take(numberOfActors)
        }
    }
}