package com.repository.guessgame.repository

import com.paris_2.domain.game.exception.FailedException
import com.paris_2.domain.game.exception.GameException
import com.paris_2.domain.game.exception.NoInternetConnectionException
import com.paris_2.domain.game.models.Actor
import com.paris_2.domain.game.repositories.ActorPopularityRepository
import com.paris_2.repository.user.dataSource.local.SettingLocalDataSource
import com.repository.guessgame.datasource.remote.ActorPopularityRemoteDataSource
import com.repository.guessgame.mapper.toDomain
import com.repository.guessgame.utils.NetworkConnectionChecker
import kotlinx.coroutines.flow.first

class ActorPopularityRepositoryImpl(
    private val networkConnectionChecker: NetworkConnectionChecker,
    private val actorPopularityDataSource: ActorPopularityRemoteDataSource,
    private val settingLocalDataSource: SettingLocalDataSource

): ActorPopularityRepository {
    override suspend fun getPopularActor(): List<Actor> {
        val language = settingLocalDataSource.getLanguage().first()
         return safeCall(FailedException("Failed to get popular actor")){
           actorPopularityDataSource.getPopularActors(language).results?.mapNotNull {
               it?.toDomain()
           } ?: emptyList()
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