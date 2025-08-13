package com.paris_2.domain.game.repositories

import com.paris_2.domain.game.entity.Actor

interface ActorPopularityRepository {
    suspend fun getPopularActor(): List<Actor>
    suspend fun getRandomActors(numberOfActors: Int): List<Actor>
}