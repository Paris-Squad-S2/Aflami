package com.repository.home

import com.repository.home.datasource.remote.MediaRemoteDataSource
import com.repository.home.dto.MediaListDto

class MediaDataSourceImpl (
    private val apiService: MediaApiService
) : MediaRemoteDataSource {

    override suspend fun getPopularMovies(): MediaListDto = apiService.getPopularMovie()

    override suspend fun getTopRatedMovies(): MediaListDto = apiService.getTopRatedMovie()

    override suspend fun getUpcomingMovies(): MediaListDto = apiService.getUpcoming()

    override suspend fun getNowPlayingMovies(): MediaListDto = apiService.getNowPlaying()

    override suspend fun getPopularTvShows(): MediaListDto = apiService.getPopularTv()

    override suspend fun getTopRatedTvShows(): MediaListDto = apiService.getTopRatedTv()
}