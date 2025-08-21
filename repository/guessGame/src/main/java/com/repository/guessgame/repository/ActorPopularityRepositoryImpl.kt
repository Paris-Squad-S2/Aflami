package com.repository.guessgame.repository

import com.paris.domain.game.entity.Actor
import com.paris.domain.game.exception.FailedException
import com.paris.domain.game.exception.GameException
import com.paris.domain.game.exception.NoInternetConnectionException
import com.paris.domain.game.repositories.ActorPopularityRepository
import com.paris.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.guessgame.datasource.remote.ActorPopularityRemoteDataSource
import com.repository.guessgame.mapper.toDomain
import com.repository.guessgame.utils.NetworkConnectionChecker
import kotlinx.coroutines.flow.first

class ActorPopularityRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val actorPopularityDataSource: ActorPopularityRemoteDataSource
): ActorPopularityRepository {
    override suspend fun getPopularActor(): List<Actor> {
         return safeCall(FailedException("Failed to get popular actor")){
           actorPopularityDataSource.getPopularActors().results?.mapNotNull {
               it?.toDomain()
           } ?: emptyList()
        }
    }

    override suspend fun getRandomActors(numberOfActors: Int): List<Actor> {
        return safeCall(FailedException("Failed to get random actors")) {
            val allActors = actorPopularityDataSource
                .getPopularActors()
                .results
                ?.mapNotNull { it?.toDomain() }
                ?: emptyList()
            allActors.shuffled().take(numberOfActors)
        }
    }


    private suspend fun <T> safeCall(exception: GameException, call: suspend () -> T): T {
    if (networkConnectionChecker.isConnected.value.not()) {
        throw NoInternetConnectionException()
    }
    return try {
        call()
    } catch (e: GameException) {
        throw e
    } catch (_: Exception) {
        throw exception
    }
}
}