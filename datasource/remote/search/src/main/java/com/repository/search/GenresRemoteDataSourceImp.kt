package com.repository.search

import com.repository.search.dataSource.remote.GenresRemoteDataSource
import com.repository.search.dto.GenresDto
import com.repository.search.service.implementation.RetrofitGenresApiServices
import javax.inject.Inject

class GenresRemoteDataSourceImp @Inject constructor(private val retrofitGenresApiServices: RetrofitGenresApiServices) :
    GenresRemoteDataSource {
    override suspend fun getAllGenres(language: String): GenresDto {
        val movieDto = retrofitGenresApiServices.getAllGenresMovie(language)
        val tvShowDto = retrofitGenresApiServices.getAllGenresTvShow(language)
        return movieDto.copy(genreDto = movieDto.genreDto.orEmpty() + tvShowDto.genreDto.orEmpty())
    }
}
