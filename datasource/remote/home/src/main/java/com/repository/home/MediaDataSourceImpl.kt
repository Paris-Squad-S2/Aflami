package com.repository.home

import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.dto.MovieListDto
import com.repository.home.dto.TvListDto

class MediaDataSourceImpl (
    private val apiService: MediaApiService
) : MediaRemoteDataSource {

    override suspend fun getPopularMovies(): MovieListDto = apiService.getPopularMovie()

    override suspend fun getTopRatedMovies(): MovieListDto = apiService.getTopRatedMovie()

    override suspend fun getUpcomingMovies(): MovieListDto = apiService.getUpcoming()

    override suspend fun getNowPlayingMovies(): MovieListDto = apiService.getNowPlaying()

    override suspend fun getPopularTvShows(): TvListDto = apiService.getPopularTv()

    override suspend fun getTopRatedTvShows(): TvListDto = apiService.getTopRatedTv()
}