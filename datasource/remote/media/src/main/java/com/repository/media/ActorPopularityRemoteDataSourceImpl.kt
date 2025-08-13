package com.repository.media

import com.repository.guessgame.datasource.remote.ActorPopularityRemoteDataSource
import com.repository.guessgame.dto.ActorPopularityListDto
import com.repository.media.services.ActorPopularityApiService
import kotlin.random.Random

class ActorPopularityRemoteDataSourceImpl(
    private val apiService : ActorPopularityApiService
): ActorPopularityRemoteDataSource {
    override suspend fun getPopularActors(language: String): ActorPopularityListDto {
        return apiService.getPopularActors(language, Random.nextInt(1, 500))
    }
}