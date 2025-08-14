package com.repository.media

import com.repository.guessgame.datasource.remote.ActorPopularityRemoteDataSource
import com.repository.guessgame.dto.ActorPopularityListDto
import com.repository.media.services.ActorPopularityApiService
import javax.inject.Inject
import kotlin.random.Random

class ActorPopularityRemoteDataSourceImpl @Inject constructor(
    private val apiService : ActorPopularityApiService
): ActorPopularityRemoteDataSource {
    override suspend fun getPopularActors(language: String): ActorPopularityListDto {
        return apiService.getPopularActors(language, Random.nextInt(1, 500))
    }
}