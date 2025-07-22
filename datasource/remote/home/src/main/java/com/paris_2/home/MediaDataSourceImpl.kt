package com.paris_2.home

import com.paris_2.home.datasource.remote.MediaRemoteDataSource
import com.paris_2.home.dto.MediaListDto

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