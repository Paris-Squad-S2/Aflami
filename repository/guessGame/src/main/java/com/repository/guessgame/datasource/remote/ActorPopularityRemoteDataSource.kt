package com.repository.guessgame.datasource.remote

import com.repository.guessgame.dto.ActorPopularityListDto

interface ActorPopularityRemoteDataSource {
    suspend fun getPopularActors(): ActorPopularityListDto
}