package com.paris_2.home.datasource.remote

import com.paris_2.home.dto.MediaListDto

interface MediaRemoteDataSource {
    suspend fun getPopularMovies(): MediaListDto
    suspend fun getTopRatedMovies(): MediaListDto
    suspend fun getUpcomingMovies():MediaListDto
    suspend fun getNowPlayingMovies(): MediaListDto
    suspend fun getPopularTvShows(): MediaListDto
    suspend fun getTopRatedTvShows(): MediaListDto
}
