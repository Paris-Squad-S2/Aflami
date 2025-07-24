package com.repository.home

import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.dto.MovieListDto
import com.repository.home.dto.TvListDto

class MediaDataSourceImpl (
    private val apiService: MediaApiService
) : MediaRemoteDataSource {

    override suspend fun getPopularMovies(language: String): MovieListDto = apiService.getPopularMovie(language)

    override suspend fun getTopRatedMovies(language: String): MovieListDto = apiService.getTopRatedMovie(language)

    override suspend fun getUpcomingMovies(language: String): MovieListDto = apiService.getUpcoming(language)

    override suspend fun getNowPlayingMovies(): MovieListDto = apiService.getNowPlaying()

    override suspend fun getPopularTvShows(language: String): TvListDto = apiService.getPopularTv(language)

    override suspend fun getTopRatedTvShows(language: String): TvListDto = apiService.getTopRatedTv(language)
}