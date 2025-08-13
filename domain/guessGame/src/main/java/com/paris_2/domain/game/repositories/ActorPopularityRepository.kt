package com.paris_2.domain.game.repositories

import com.paris_2.domain.game.entity.Actor

interface ActorPopularityRepository {
    suspend fun getPopularActor(): List<Actor>
}