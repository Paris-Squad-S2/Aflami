package com.paris.domain.game.repositories

import com.paris.domain.game.entity.Actor

interface ActorPopularityRepository {
    suspend fun getPopularActor(): List<Actor>
    suspend fun getRandomActors(numberOfActors: Int): List<Actor>
}