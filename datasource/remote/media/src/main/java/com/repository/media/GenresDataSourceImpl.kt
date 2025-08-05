package com.repository.media

import com.repository.media.datasource.remote.GenresRemoteDataSource
import com.repository.media.dto.GenresDto
import com.repository.media.services.GenresApiServices
import javax.inject.Inject

class GenresDataSourceImpl @Inject constructor(
    private val apiService: GenresApiServices
): GenresRemoteDataSource {
    override suspend fun getMoviesGenres(language: String): GenresDto {
        return apiService.getMoviesGenres(language)
    }

    override suspend fun getAllGenres(language: String): GenresDto {
        val movieDto = apiService.getMoviesGenres(language)
        val tvShowDto = apiService.getTvShowsGenres(language)
        return movieDto.copy(genreDto = movieDto.genreDto.orEmpty() + tvShowDto.genreDto.orEmpty())
    }
}