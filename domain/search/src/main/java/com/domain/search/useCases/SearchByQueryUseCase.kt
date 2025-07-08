package com.domain.search.useCases

import com.domain.search.model.Media

class SearchByQueryUseCase (
    // private val movieRepository: MovieRepository,
    // private val tvShowRepository: TvShowRepository
){

    suspend operator fun invoke(query: String): List<Media> {
        return TemporaryFakeData.mediaList
        /*

val allMediaDeferred = async { movieRepository.getAllMovies() + tvShowRepository.getAllTvShows() }
val filteredByActorDeferred = async { movieRepository.getMoviesByActor(query) + tvShowRepository.getTvShowsByActor(query) }
val filteredByCountryDeferred = async { tvShowRepository.getMoviesByCountry(query) }

val allMedia = allMediaDeferred.await()
val filteredByTitleOrCategory = allMedia.filter {
    it.title.contains(query, ignoreCase = true) ||
            it.categories.any { category ->
                category.contains(query, ignoreCase = true)
            } ||
            it.yearOfRelease.toString().contains(query, ignoreCase = true)
}
val filteredByActor = filteredByActorDeferred.await()
val filteredByCountry = filteredByCountryDeferred.await()
(filteredByTitleOrCategory + filteredByActor + filteredByCountry).distinctBy { it.id }
         */
    }
}


