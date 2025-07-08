package com.domain.search.useCases

import com.domain.search.model.Media

class GetMediaByActorName(
    // private val movieRepository: MovieRepository,
    // private val tvShowRepository: TvShowRepository
){

    suspend operator fun invoke(actorName: String): List<Media> {
        return TemporaryFakeData.mediaList
        /*
    val moviesDeferred = async { movieRepository.getMoviesByActor(actorName) }
    val tvShowsDeferred = async { tvShowRepository.getTvShowsByActor(actorName) }
    moviesDeferred.await() + tvShowsDeferred.await()
         */
    }
}